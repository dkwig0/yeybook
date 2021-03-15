let myname = '';

var page = 0;
const pageSize = 5;

var stomp;

connect()

$(document).ready(function () {
    myname = $.ajax({
        url: 'api/users/me',
        type: 'GET',
        dataType: 'json',
        success: function (user) {
            myname = Object.assign({}, user).username;
        }
    });
});

$('.side-bar').ready(function () {
    $('.navigation > .section > .item').first().trigger('click');
});

$('.messages').ready(function () {
    scrollDown();
});

$('.chats > .items').ready(function () {
    loadChats()
});

$(window).resize(function () {
   $('.chats > .selector').css('display', 'none');
});

function newMessageInput(input) {
    $('.new-message').css('height', 40 + 'px');
    if ($(input).outerHeight() < input.scrollHeight) {
        $('.new-message').css('height', input.scrollHeight);
        $('.messages').css('bottom', input.scrollHeight + 20);
    }
    if ($(input).outerHeight() > $('.new-message').outerHeight(true)) {
        $('.new-message').css('height', $(input).outerHeight(true));
    }
    $('.messages').css('bottom', $('.new-message').outerHeight(true) + 20 + 'px');
}

function moveChatSelector() {
    let types = $('.chats > .types');
    let selector = $('.chats > .selector');
    if (types.outerHeight() + types.position().top > selector.position().top) {
        selector.css('display', 'none');
    }
    $('.chats > .selector').css({'top': $('.items > .item.showed').position().top + 'px'});
};
function showChat(chat) {

    loadChat($(chat).attr('id'));

    hideChatSelector();

    let selector = $('.chats > .selector');

    chat.scrollIntoView({block: 'center', behavior: 'smooth'});

    setTimeout(function () {


        $('.items > .item.showed').removeClass('showed');
        $(chat).addClass('showed');
    }, 300);

    setTimeout(function () {

        selector.css({'left': $('.items').width() - (selector.width()
                + parseInt($('.items > .item').first().css('margin').split('px')[0], 10) * 2) + 10 + 'px',
            'top': $(chat).position().top + 'px'});
        selector.css('display', 'block');
        openChatSelector();
    }, 300);
}

function openChatSelector() {
    let selector = $('.chats > .selector');
    $('.chats > .selector > .section > .point').animate({opacity: 1}, 200);
    selector.animate({left: selector.position().left + 30}, 200);
    setTimeout(function () {
        selector.css('display', 'block');
    }, 200);
}

function hideChatSelector() {
    let selector = $('.chats > .selector');
    selector.animate({left: selector.position().left - 30}, 200);
    $('.chats > .selector > .section > .point').animate({opacity: 0}, 200);
    setTimeout(function () {
        selector.css('display', 'none');
    }, 200);
}

function showUser() {
    if ($('.user').hasClass('show')) {
        $('.main-info > .hidden').animate({opacity: 0}, 200);
        setTimeout(function () {
            $('.main-info > .hidden').css('display', 'none');
            $('.user').toggleClass('show');
        }, 200);
    } else {
        $('.main-info > .hidden').css('display', 'inline-block');
        setTimeout(function () {
            $('.main-info > .hidden').animate({opacity: 1}, 200);
        }, 600);
        $('.user').toggleClass('show');
    }

}

function sideBarSelect(item) {
    $('.navigation > .section > .item.selected').removeClass('selected')
    $(item).addClass('selected')
}

function showAnonymous() {
    $('.types > .item:nth-of-type(1)').removeClass('selected')
    $('.types > .item:nth-of-type(2)').addClass('selected')
    $('.chats > .selector').removeClass('signed');
    $('.chats > .selector').addClass('anonymous');
    $('.chats > .selector').css('display', 'none');
    $('.chats > .items > .item.anonymous').css('display', 'block');
    $('.chats > .items > .item.signed').css('display', 'none');
}

function showSigned() {
    $('.types > .item:nth-of-type(2)').removeClass('selected')
    $('.types > .item:nth-of-type(1)').addClass('selected')
    $('.chats > .selector').addClass('signed');
    $('.chats > .selector').removeClass('anonymous');
    $('.chats > .selector').css('display', 'none');
    $('.chats > .items > .item.anonymous').css('display', 'none');
    $('.chats > .items > .item.signed').css('display', 'block');
}

function scrollDown() {
    $('.messages').get(0).scrollTo(0, $('.messages').get(0).scrollHeight);
}

function loadChats() {
    let chats = $('.chats > .items');

    chats.empty();

    $.ajax({
        url: 'api/users/me',
        type: "GET",
        dataType: "json",
        success: function (user) {
            stomp.connect({}, frame => {
                console.log(frame);
            for (let i = 0; i < user.chatRooms.length; i++) {
                $.ajax({
                    url: 'api/chats/' + user.chatRooms[i].id,
                    type: 'GET',
                    dataType: 'json',
                    success: function (chat) {
                        let lastMessage;
                        try {
                            lastMessage = chat.messages[chat.messages.length - 1].text;
                        } catch (e) {
                            lastMessage = '';
                        }
                        $('.chats > .items').append('<div class="item signed" onclick="showChat(this)" id="' + user.chatRooms[i].id + '">' +
                            '<div class="left">' +
                            '<div class="avatar">' +
                            '<div class="image"></div>' +
                            '</div>' +
                            '</div>' +
                            '<div class="right">' +
                            '<div class="name">' + chat.users
                                .filter(u => u.username != user.username)
                                .map(u => u.username)
                                .reduce((u1, u2) => u1 + ', ' + u2) + '</div>' +
                            '<div class="last-message">' + lastMessage + '</div>' +
                            '</div>' +
                            '</div>');
                        subscribe(user.chatRooms[i].id);
                    }
                });
            }
            })
        }

    });

}

function loadChat(id) {
    page = 1;
    $('.messages').html('')
    $.ajax({
        url: 'api/chats/' + id + '/messages?page=1&size=' + pageSize,
        type: 'GET',
        dataType: 'json',
        success: function (messages) {
            $('.chat').attr('id', id);
            for (let i = 0; i < messages.length; i++) {
                prependMessage(messages[i]);
            }
            $('.chat > .messages').prepend('<button onclick="loadMore()">load more</button>')
            page++;
            scrollDown();
        }
    });
}

function loadMore() {
    $.ajax({
        url: 'api/chats/' + $('.chat').attr('id') + '/messages?page=' + page + '&size=' + pageSize,
        type: 'GET',
        dataType: 'json',
        success: function (messages) {
            $('.chat > .messages > button').remove()
            console.log(messages)
            for (let i = 0; i < messages.length; i++) {
                prependMessage(messages[i]);
            }
            if (messages.length === pageSize) {
                $('.chat > .messages').prepend('<button onclick="loadMore()">load more</button>')
            }
            page++;
        }
    });
}

function connect() {
    var sock = new SockJS('/messages')
    stomp = Stomp.over(sock);

}

function subscribe(id) {
    stomp.subscribe('/topic/' + id, function (message) {
        const soundEffect = new Audio('sound.wav');
        soundEffect.play();
        appendMessage(JSON.parse(message.body));
    });
}

function sendMessage() {

    stomp.send('/chat/' + $('.chat').first().attr('id'), {}, JSON.stringify({'text':
            $('.new-message > .input > .text').html(),
            'chatRoom': {'id': $('.chat.showed').attr('id')}}));
}

function appendMessage(message) {
    let whose;
    if (message.user.username == myname) {
        whose = 'predator';
    } else {
        whose = 'alien';
    }
    $('.chat > .messages').append('<div class="message ' + whose + '">' +
        '<div class="section">' +
        '<div class="text">' +
        '<div class="the-text-itself">' + message.text + '</div>' +
        '</div>' +
        '</div>' +
        '</div>');
    scrollDown()
}

function prependMessage(message) {
    let whose;
    if (message.user.username == myname) {
        whose = 'predator';
    } else {
        whose = 'alien';
    }
    $('.chat > .messages').prepend('<div class="message ' + whose + '">' +
        '<div class="section">' +
        '<div class="text">' +
        '<div class="the-text-itself">' + message.text + '</div>' +
        '</div>' +
        '</div>' +
        '</div>');
    scrollDown()
}

class Message {
    constructor(text, user, chatRoom, date, id) {
        this.text = text;
        this.user = user;
        this.chatRoom = chatRoom;
        this.date = date;
        this.id = id;
    }


}

class ChatRoom {
    constructor(id, users, messages, anon) {
        this.id = id;
        this.users = users;
        this.messages = messages;
        this.anonymous = anon;
    }
}
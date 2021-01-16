let myname = '';

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
                + parseInt($('.items > .item').first().css('margin').split('px')[0], 10) * 2) + 'px',
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
    $('.navigation > .section > .selector').css('top', $(item).position().top);
}

function showAnonymous() {
    $('.chats > .selector').removeClass('signed');
    $('.chats > .selector').addClass('anonymous');
    $('.chats > .selector').css('display', 'none');
    $('.chats > .items > .item.anonymous').css('display', 'block');
    $('.chats > .items > .item.signed').css('display', 'none');
}

function showSigned() {
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
                    }
                });
            }

        }

    });

}

function loadChat(id) {
    $('.chat > .messages').empty();
    $.ajax({
        url: 'api/chats/' + id,
        type: 'GET',
        dataType: 'json',
        success: function (chat) {
            $('.chat').attr('id', chat.id);
            let messages = chat.messages;
            messages.sort(function (m1, m2) {
                if (new Date(m1.date).getTime() > new Date(m2.date).getTime())
                    return 1;
                else if (new Date(m1.date).getTime() < new Date(m2.date).getTime())
                    return -1;
                else
                    return 0;
            });
            for (let i = 0; i < messages.length; i++) {
                let whose;
                if (messages[i].user.username == myname) {
                    whose = 'predator';
                } else {
                    whose = 'alien';
                }
                $('.chat > .messages').append('<div class="message ' + whose + '">' +
                        '<div class="section">' +
                            '<div class="text">' +
                                '<div class="the-text-itself">' + messages[i].text + '</div>' +
                            '</div>' +
                            '<div class="bubbles">' +
                                '<div class="bubble"></div>' +
                                '<div class="bubble"></div>' +
                            '</div>' +
                        '</div>' +
                        '<div class="avatar">' +
                            '<div class="image"></div>' +
                        '</div>' +
                    '</div>');
            }

        }
    });
}

function sendMessage() {
    $.ajax({
        url: 'api/chats/' + $('.chat').attr('id'),
        type: 'GET',
        dataType: 'json',
        success: function (chat) {
            $.ajax({
                url: 'api/messages',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(new Message($('.new-message > .input > .text').html(),
                    null, new ChatRoom(chat.id, null, null, null), null, null)),
                success: function () {

                }
            });
        }
    });

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
$('.side-bar').ready(function () {
    $('.navigation > .section > .item').first().trigger('click');
});

$('.messages').ready(function () {
    scrollDown();
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
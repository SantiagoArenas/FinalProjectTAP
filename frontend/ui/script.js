window.onscroll = function() {
    if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        document.querySelector(".nav").classList.add("small-nav");
    } else {
        document.querySelector(".nav").classList.remove("small-nav");
    }
};
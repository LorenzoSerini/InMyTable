var menuItem = document.getElementsByClassName("menu__list__item");
var pages = [];

var changePage = function() {
  var pageNum = this.getAttribute("data-page");
  var pageContainer = document.getElementsByClassName("pages")[0];
  for (var i = 0; i < pages.length; i++) {
    if (pageContainer.classList)
      pageContainer.classList.remove(pages[i]);
    else
      pageContainer.className = pageContainer.className.replace(new RegExp('(^|\\b)' + pages[i].split(' ').join('|') + '(\\b|$)', 'gi'), ' ')
  };
  if (pageContainer.classList)
    pageContainer.classList.add("pages--" + pageNum);
  else
    pageContainer.className += ' ' + "pages--" + pageNum;
  };

for (var i = 0; i < menuItem.length; i++) {
  menuItem[i].addEventListener('click', changePage, false);
  pages.push("pages--" + (i + 1));
}
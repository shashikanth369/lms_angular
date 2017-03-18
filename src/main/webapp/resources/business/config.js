lmsApp.config(["$routeProvider",function($routeProvider){
    return $routeProvider.when("/", {
        redirectTo : "/home"
    }).when("/home", {
        templateUrl : "home.html"
    }).when("/admin", {
        templateUrl : "administrator.html"
    }).when("/author", {
        templateUrl : "author.html"
    }).when("/initAuthor", {
        templateUrl : "addauthor.html"
    }).when("/viewauthors", {
        templateUrl : "viewauthors.html"
    }).when("/librarian", {
        templateUrl : "librarian.html"
    }).when("/publisher", {
        templateUrl : "publisher.html"
    }).when("/branch", {
        templateUrl : "branch.html"
    }).when("/book", {
        templateUrl : "book.html"
    }).when("/Borrower2", {
        templateUrl : "Borrower2.html"
    }).when("/addPub", {
        templateUrl : "addpublisher.html"
    }).when("/viewPub", {
        templateUrl : "viewpublisher.html"
    }).when("/addbranch", {
        templateUrl : "addbranch.html"
    }).when("/addbook", {
        templateUrl : "addbook.html"
    }).when("/viewbook", {
        templateUrl : "viewbook.html"
    }).when("/displayBooks", {
        templateUrl : "displayBooks.html"
    }).when("/Borrower2", {
        templateUrl : "Borrower2.html"
    }).when("/addborrower", {
        templateUrl : "addborrower.html"
    }).when("/viewborrower", {
        templateUrl : "viewborrower.html"
    }).when("/aborrower1", {
        templateUrl : "aborrower1.html"
    }).when("/aborrower", {
        templateUrl : "aborrower.html"
    }).when("/library", {
        templateUrl : "library.html"
        }).when("/editbookcopies", {
            templateUrl : "editbookcopies.html"
    }).when("/borrower", {
        templateUrl : "borrower.html"
    })
}])

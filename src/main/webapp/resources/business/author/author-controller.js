lmsApp.controller("authorController", function($scope, $http, $location, $window, authorService, Pagination,  toastr){

    $scope.selectBorrowerIndex = null;
    $scope.selectedBorrowerIndex = null;
    $scope.selectLibIndex = null;
    $scope.selectPubIndex = null;
    $scope.selectedPubIndex = null;
    $scope.selectauthorIndex = null;
    $scope.selectBookIndex = null;
    $scope.selectedBookIndex = null;
    $scope.selectedAuthor = null;
    $scope.selectAuthor = function(author, index){
        $scope.selectauthorIndex = index;
        $scope.selectedAuthor = author;
    };
    $scope.selectBook = function(book,index){
        $scope.selectBookIndex = index;
        $scope.selectedBookIndex = book;
    };
    $scope.selectBorrower = function(borrower, index){
        $scope.selectBorrowerIndex = index;
        $scope.selectedBorrowerIndex = borrower;
    };
    $scope.selectLib = function(index){
        $scope.selectLibIndex = index;
    }
    $scope.selectPub = function(publisher, index){
        $scope.selectPubIndex = index;
        $scope.selectedPubIndex = publisher;
    }

   // if($location.$$path === "/viewauthors") {
        authorService.getAllAuthors().then(function (data) {
            $scope.authorsList = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.authorsList.length / $scope.pagination.perPage);
        })

   // }else {
        $http.get("http://localhost:9090/initAuthor").success(function (data) {
        $scope.author = data;

    })
    $http.get("http://localhost:9090/initBranch").success(function (data) {
        $scope.branch = data;

    })
    $http.get("http://localhost:9090/initBookCopies").success(function (data) {
        $scope.bookCopies = data;

    })
    $http.get("http://localhost:9090/initPublisher").success(function (data) {
        $scope.publisher = data;
    })
    $http.get("http://localhost:9090/initBook").success(function (data) {
        $scope.book = data;
    })
    $http.get("http://localhost:9090/initBorrower").success(function (data) {
        $scope.borrower = data;
    })
    $http.get("http://localhost:9090/initBookLoans").success(function (data) {
        $scope.bookLoan = data;
    })
    $http.get("http://localhost:9090/readAllBookLoans/").success(function(data) {
        $scope.blList = data;
    })

    $http.get("http://localhost:9090/readAllGenre/").success(function(data){
        $scope.genreList1 = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.genreList1.length / $scope.pagination.perPage);

    })
    $http.get("http://localhost:9090/viewBorrower/").success(function(data){
        $scope.borrowerList = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.borrowerList.length / $scope.pagination.perPage);

    })


   // }
   //  $http.get("http://localhost:9090/viewbooks/").success(function(data){
   //      $scope.bookList1 = data;
   //
   //
   //  })


    $http.get("http://localhost:9090/viewpublishers/").success(function(data){
        $scope.publisherList = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.publisherList.length / $scope.pagination.perPage);

    })
    $http.get("http://localhost:9090/readAllBranches/").success(function(data){
        $scope.branchList = data;
        console.log(data);
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.branchList.length / $scope.pagination.perPage);

    })
    $http.get("http://localhost:9090/viewbooks/").success(function(data){
        $scope.bookList2 = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.bookList2.length / $scope.pagination.perPage);

    })

    /*if($location.$$path === "/viewbooks") {
        authorService.getBooks().then(function (data) {
            $scope.bookList = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.bookList.length / $scope.pagination.perPage);
        })
    }else {
        $http.get("http://localhost:9090/initBook").success(function (data) {
            $scope.book = data;

        })
    }*/
    $scope.saveAuthor  = function() {
        $http.post("http://localhost:9090/addAuthor", $scope.author).success(function (data) {
            toastr.info('Author added successfully', 'Added');
            authorService.getAllAuthors().then(function (data) {
                $scope.authorsList = data;
                $window.location.href = "#/viewauthors";
            })

        })
    }
    $scope.saveBorrower = function() {
        $http.post("http://localhost:9090/addBorrower", $scope.borrower).success(function (data) {
            toastr.info('Borrower added successfully', 'Added');
            console.log("After add method");
            $http.get("http://localhost:9090/viewBorrower/").success(function(data) {
                $scope.borrowerList = data;
                $window.location.href = "#/viewborrower";
            })
        })
    }
    $scope.saveBook = function() {
        $http.post("http://localhost:9090/addbooks", $scope.book).success(function(data){
            toastr.info('Book added successfully', 'Added');
            $http.get("http://localhost:9090/viewbooks/").success(function(data) {
                $scope.bookList2 = data;
                $window.location.href = "#/viewbook";

            })
        })

    }
    $scope.saveBranch = function() {
        $http.post("http://localhost:9090/addBranch", $scope.branch).success(function (data) {
            toastr.info('Branch added successfully', 'Added');
            $http.get("http://localhost:9090/readAllBranches/").success(function (data) {
                $scope.branchList = data;
                $scope.pagination = Pagination.getNew(10);
                $scope.pagination.numPages = Math.ceil($scope.branchList.length / $scope.pagination.perPage);
                console.log("3");
                $window.location.href = "#/librarian";
                console.log("33");
            })
        })
    }
    $scope.savePublisher = function(){
        $http.post("http://localhost:9090/addpublishers",$scope.publisher).success(function (data){
            toastr.info('Publisher added successfully', 'Added');
            $http.get("http://localhost:9090/viewpublishers/").success(function(data) {
                $scope.publisherList = data;
                $window.location.href = "#/viewPub";
            })
        })
    }
    $scope.deleteAuthor = function(authorId) {
        $http.get("http://localhost:9090/deleteAuthor/" + authorId).success(function (data) {
            toastr.warning('You have deleted author', 'Warning');
            authorService.getAllAuthors().then(function (data) {
                $scope.authorsList = data;

            })
        })
    }
    $scope.checkBL = function(branchId, cardNo){
        $http.get("http://localhost:9090/readBookBycardNo/"+branchId+"/"+cardNo).success(function(data){
            $scope.branchId1 = branchId;
            $scope.cardNo1 = cardNo;
            $scope.checkInList = data;
            $scope.bookLoanModal = true;

        })

    }
    $scope.CheckInBook = function(bookId){
        $scope.bookLoanModal = false;
        $http.get("http://localhost:9090/bookCheckIn/"+bookId+"/"+$scope.cardNo1).success(function(data){
            toastr.info('Book Checked In successfully', 'Added');
        $http.get("http://localhost:9090/deleteBookLoan1/"+bookId+"/"+$scope.branchId1+"/"+ $scope.cardNo1).success(function(data){
            $scope.bookLoanModal = false;

            $window.location.href = "#/aborrower";
        })
        })
    }



    $scope.deleteBorrower = function(cardNo){
        $http.get("http://localhost:9090/deleteBorrower/"+cardNo).success(function (data) {
            toastr.warning('You have deleted Borrower', 'Warning');
            $http.get("http://localhost:9090/viewBorrower/").success(function(data) {
                $scope.borrowerList = data;

            })
        })
    }
    $scope.deleteBook = function(bookId){
        $http.get("http://localhost:9090/deleteBook/"+bookId).success(function(data) {
            toastr.warning('You have deleted Book', 'Warning');
            $http.get("http://localhost:9090/viewbooks/").success(function (data) {
                $scope.bookList2 = data;

            })
        })
    }

    $scope.deleteBranch = function(branchId) {
        $http.get("http://localhost:9090/deleteBranch/" + branchId).success(function (data) {
            toastr.warning('You have deleted Branch', 'Warning');
            $http.get("http://localhost:9090/readAllBranches/").success(function (data) {
                $scope.branchList = data;

            })

        })
    }
    $scope.deletePub = function(publisherId){
        $http.get("http://localhost:9090/deletePublisher/"+publisherId).success(function (data){
            toastr.warning('You have deleted author', 'Warning');
            $http.get("http://localhost:9090/viewpublishers/").success(function (data) {
                $scope.publisherList = data;

            })

        })
    }
    $scope.editDetails = function(branchId){
        $http.get("http://localhost:9090/readBranchByID/"+branchId).success(function(data){
            $scope.branch = data;
            $scope.editBranchModal = true;
        })
    }
    $scope.bookCopiesModal =  function(bookId, branchId){
        $http.get("http://localhost:9090/readBookCopies/"+bookId+"/"+branchId ).success(function(data){
            $scope.bookCopies = data;
            $scope.editCopiesModal = true;
        })
    }
    $scope.editCopies  = function(branchId){
        $http.get("http://localhost:9090/displayBookCopies/"+branchId).success(function(data){
            $scope.copiesList = data;
            $scope.copiesList1=$scope.copiesList;
            console.log($scope.copiesList);
            $window.location.href = "#/displayBooks";

        })

    }


    $scope.showeditModal = function(authorId){
        $http.get("http://localhost:9090/readAuthorById/"+authorId).success(function(data){
            $scope.author = data;
            $scope.editAuthorModal = true;
        })
    }
    $scope.showBookModal = function(bookId){
        console.log("in");
        $http.get("http://localhost:9090/readBookByID/"+bookId).success(function(data){
            console.log("after");
            $scope.book = data;
            $scope.editBookModal1 = true;
        })
    }
    $scope.editPubModal2  = function(publisherId){
        $http.get("http://localhost:9090/readPublisherByID/"+publisherId).success(function(data){
            $scope.publisher = data;
            $scope.editPubModal = true;
        })

    }
    $scope.editBookModal = function(bookId){
        $http.get("http://localhost:9090/readBookByID/"+bookId).success(function(data) {
            $scope.book = data;
            $scope.editBookModal = true;
        })

    }
    $scope.borrowerModal = function(cardNo)
    {
        $http.get("http://localhost:9090/readBorrowerByID/" + cardNo).success(function (data) {
            $scope.borrower = data;
            $scope.editBorrowerModal = true;
        })

    }
    $scope.closeEditModal = function(){
        $scope.editAuthorModal = false;
        $scope.editBranchModal = false;
        $scope.editPubModal = false;
       $scope.editBookModal = false;
        $scope.editCopiesModal = false;
        $scope.editBorrowerModal = false;
        $scope.editCopiesModal = false;
        $scope.bookLoanModal = false;
       $scope.editBookModal1 = false;

    }
    $scope.updateAuthor = function(author){
        $scope.editAuthorModal = false;
        $http.post("http://localhost:9090/updateAuthor", author).success(function(data){
            toastr.success('Updated Author successfully', 'UPDATED');
            $scope.editAuthorModal = false;
            authorService.getAllAuthors().then(function (data) {
                $scope.authorsList = data;
            })

        })
    }
    $scope.updateBookCopies = function(bookCopies){
        $scope.editCopiesModal = false;
        $http.post("http://localhost:9090/updateBookCopies", bookCopies).success(function(data){
            toastr.success('Updated Book Copies successfully', 'UPDATED');
            $scope.editCopiesModal = false;
        })
    }
    $scope.updateBook = function(book){
        $scope.editBookModal = false;
        $http.post("http://localhost:9090/updateBook",book).success(function(data) {
            toastr.success('Updated Book successfully', 'UPDATED');
            $scope.editBookModal1 = false;
            $http.get("http://localhost:9090/viewbooks/").success(function (data) {
                $scope.bookList2 = data;
            })
        })
    }
    $scope.updateBorrower = function(borrower){
        $scope.editBorrowerModal = false;
        $http.post("http://localhost:9090/updateBorrower",borrower).success(function(data) {
            toastr.success('Updated Borrower successfully', 'UPDATED');
            $scope.editBorrowerModal = false;
            $http.get("http://localhost:9090/viewBorrower/").success(function(data){
                $scope.borrowerList = data;
            })
        })


    }
    $scope.updatePub = function(publisher){
        $scope.editPubModal = false;
        $http.post("http://localhost:9090/updatePublisher", publisher).success(function(data){
            toastr.success('Updated Publisher successfully', 'UPDATED');
            $scope.editPubModal = false;
            $http.get("http://localhost:9090/viewpublishers/").success(function (data) {
                $scope.publisherList = data;
            })
        })

    }
    $scope.exitToLibrary = function(){
        $window.location.href = "#/librarian";
    }
 /*   $scope.updateCopies = function(bookCopies) {
        $scope.editCopiesModal = false;
        $http.post("http://localhost:9090/updateBookCopies", bookCopies).success(function (data) {
            $scope.editCopiesModal = false;
            $http.get("http://localhost:9090/displayBookCopies/" + branchId).success(function (data) {
                $scope.copiesList = data;


            })
        })
    }*/
    $scope.updateBranch = function(branch) {
        $scope.editBranchModal = false;
        $http.post("http://localhost:9090/updateBranch", branch).success(function (data) {
            toastr.success('Updated Branch successfully', 'UPDATED');
            $scope.editBranchModal = false;
            $http.get("http://localhost:9090/readAllBranches/").success(function (data){
                $scope.branchList = data;

            })

        })
    }
    $scope.searchBranch = function(){
        $http.get("http://localhost:9090/readBranchByName/"+$scope.searchBranchName).success(function(data){
            $scope.branchList = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.branchList.length / $scope.pagination.perPage);
        })
    }
    $scope.searchBook = function(){
        $http.get("http://localhost:9090/readBooksByName/"+$scope.searchBookName).success(function(data){
            $scope.bookList2 = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.bookList2.length / $scope.pagination.perPage);

        })
    }
    $scope.searchBorrower = function(){
        $http.get("http://localhost:9090/searchBorrower/"+$scope.searchBorrowerName).success(function(data){
                $scope.borrowerList = data;
                $scope.pagination = Pagination.getNew(10);
                $scope.pagination.numPages = Math.ceil($scope.borrowerList.length / $scope.pagination.perPage);

        })
    }
    $scope.searchPublisher = function(){
        $http.get("http://localhost:9090/readPublishersByName/"+$scope.searchPublisherName).success(function(data){
            $scope.publisherList = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.publisherList.length / $scope.pagination.perPage);
        })
    }
    $scope.searchAuthor = function(){
        $http.get("http://localhost:9090/searchauthors/"+$scope.searchAuthorName).success(function(data){
            $scope.authorsList = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.authorsList.length / $scope.pagination.perPage);
        })
    }

})
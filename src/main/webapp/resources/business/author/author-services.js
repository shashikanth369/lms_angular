lmsApp.factory("authorService", function($http, authorConstants){
    var allAuthors = {};
    var authorsByPk = {};
    var allBooks =  {};
    var allBranches = {};
    return {
        getAllAuthors : function(){
            return $http({
                method : 'GET',
                url : authorConstants.GET_AUTHORS_URL,
            }).success(function(data){
                allAuthors = data;
            }).then(function(){
                return  allAuthors;
            })
        },
        getAllBranches : function(){
            return $http({
                method : 'GET',
                url : "http://localhost:9090/readAllBranches/",
            }).success(function(data){
                allBranches = data;
            }).then(function(){
                return allBranches;
            })
        },
        getBooks : function(){
            return $http({
                method : 'GET',
                url : authorConstants.GET_BOOKS_URL,
            }).success(function(data){
                allBooks = data;
            }).then(function(){
                return allBooks;
            })
        }
    }
    })



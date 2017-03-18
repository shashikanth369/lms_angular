/**
 * Created by shash on 3/12/2017.
 */
lmsApp.factory("branchService", function($http){
    var allBranches = {};
    return{
        getAllBranches : function(){
            return $http({
                method : 'GET',
                url : "http://localhost:9090/readAllBranches/",
            }).success(function(data){
                allBranches = data;
            }).then(function(){
                return allBranches;
            })
        }
    }

})
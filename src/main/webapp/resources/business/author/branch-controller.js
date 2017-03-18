/**
 * Created by shash on 3/12/2017.
 */
lmsApp.controller("branchController", function($scope, $http, $location, $window, branchService, Pagination) {
    branchService.getAllBranches().then(function(data){
        $scope.branchList = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.branchList.length / $scope.pagination.perPage);
    })
    $http.get("http://localhost:9090/initBranch").success(function (data) {
        $scope.branch = data;

    })

})

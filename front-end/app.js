'use strict'

var app = angular.module('discussionBoardApp', ['ui.router',
    'discussionBoardServices','discussionBoardControllers']);

/*app UI routing config*/
app.config([
'$stateProvider',
'$urlRouterProvider',
function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: '/login.html',
                controller: 'LoginCtrl'
            })
            .state('home', {
                url: '/home',
                templateUrl: '/home.html',
                controller: 'MainCtrl',
                //using authentication service to check if user have the right access to the routing template
                onEnter: ['$state','authentication','$window',function($state,authentication,$window){
                        var o = $window.localStorage["accessAuth"];
                        if (!o || JSON.parse(o) === null || JSON.parse(o)
                        .login !== 'true') {
                            $state.go('login');
                        }
                }]
            })
            .state('posts', {
                url: '/posts/{id}',
                templateUrl: '/posts.html',
                controller: 'PostsCtrl',
                //using authentication service to check if user have the right access to the routing template
                onEnter: ['$state','authentication','$window',function($state,authentication,$window){
                        var o = $window.localStorage["accessAuth"];
                        if (!o || JSON.parse(o) === null || JSON.parse(o)
                        .login !== 'true') {
                            $state.go('login');
                        }
                }],
                resolve: {
                    idPromise: ['topics', '$stateParams', function(
                        topics, $stateParams) {
                        return $stateParams.id;
                }]
                }
            });
        $urlRouterProvider.otherwise('home');
}]);


/*replyDialog directive*/
app.directive('replyDialog', function() {
    return {
        restrict: 'E',
        scope: {
            replies: '=',
            repId: '=id',
            pop: '='
        },
        controller: ['$scope', 'reply', 'likeAndDislike','$state', function(
            $scope, reply, likeAndDislike,$state) {

            $scope.showModal = false;

            $scope.toggleModal = function() {
                $scope.showModal = !$scope.showModal;
            };

            $scope.replyComment = function() {
                if ($scope.content === '') {
                    return;
                }
                //console.log($scope.repId);
                reply.replyPost($scope.repId, $scope.content).then(function(status){
                    if(status === 200) {
                        $state.go($state.current,{},{reload: true});
                    }
                });
                // $scope.replies.push({
                //     author: $scope.author,
                //     content: $scope.content
                // });
                // $scope.pop += 2;
                // $scope.content = '';
                // $scope.author = '';
                // console.log($scope.pop);
            };

            $scope.incrementLike = function(reply) {
                if(reply.liked === false) {
                    reply.liked = true;
                    likeAndDislike.likePost(reply.id);
                    reply.numberLike += 1;
                    reply.popularity += 1;
                }
            };
            
            $scope.incrementDisLike = function(reply) {
               if(reply.disLiked === false) {
                    reply.disLiked = true;
                    likeAndDislike.dislikePost(reply.id);
                    reply.numberDisLike += 1;
                    reply.popularity += 1;
               }
            };
    }],
        template: '<div>' +
            '<span ng-click="toggleModal()" class="btn btn-default">reply</span>' +
            '<div ng-repeat="reply in replies| orderBy: \'-popularity\' ">' +
            '<span> {{reply.content}} </span>' +
            '<span class="glyphicon glyphicon-thumbs-up" ng-click="incrementLike(reply)"></span>' +
            '{{reply.numberLike}} - '+
            '<span class="glyphicon glyphicon-thumbs-down" ng-click="incrementDisLike(reply)"></span>'+
            '{{reply.numberDisLike}}'+
            '</div>' +
            '<div ng-show="showModal">' +
            '<form ng-submit="replyComment()">' +
            '<div class="form-group">' +
            '<input type="text" class="form-control" placeholder="Comment" ng-model="content"></input>' +
            '</div>' +
            '<button type="submit" class="btn btn-primary">Reply</button>' +
            '</form>' +
            '</div>' +
            '</div>',
    };
});


'use strict'

/*Controllers*/

var discussionBoardControllers = angular.module('discussionBoardControllers', ['discussionBoardServices']);


/*login controller*/
discussionBoardControllers.controller('LoginCtrl', [
  '$scope',
  'authentication',
  '$state',
  function($scope, authentication, $state) {
        $scope.Login = function() {
            if ($scope.username === '' || $scope.password === '') {
                alert('You dont fill in enough information');
                return;
            }
            authentication.login($scope.username, $scope.password)
                .then(function(data) {
                    if (data.login === 'true') {
                        //save username
                        authentication.setUserInfo($scope.username);
                        $state.go('home');
                    } else
                        $state.go('login');
                });
        };
}]);

                                        
/*main controller */
discussionBoardControllers.controller('MainCtrl', [
'$scope',
'topics',
'likeAndDislike',
'authentication',
'$state',
function($scope, topics, likeAndDislike,authentication, $state) {
        
        //promise of all topics
        var promise = topics.topicsPromise();
        promise.then(function(data) {
            $scope.posts = data;
        });

        $scope.addPost = function() {
            if (!$scope.title || $scope.title === '') {
                return;
            }
            topics.topicsPost($scope.content, $scope.title)
                .then(function(status) {
                    if(status === 200) {
                        $state.go($state.current,{},{reload: true});
                    }
                    //var newUser = {};
                    //get username to display the author of comment
                    // newUser.name = authentication.getUserInfo();
                    // $scope.posts.push({
                    //     title: $scope.title,
                    //     content: $scope.content,
                    //     numberLike: 0,
                    //     numberDisLike: 0,
                    //     popularity: 0,
                    //     listReply: [],
                    //     id: data,
                    //     createdUser: newUser
                    // });
                    // $scope.title = '';
                    // $scope.content = '';

                });
        };

        $scope.logout = function() {
            authentication.logout()
                .then(function(data) {
                    if (data === null) {
                        $state.go('login');
                    }
                });
        };

        $scope.incrementLike = function(post) {
            if (post.liked === false) {
                post.liked = true;
                likeAndDislike.likePost(post.id);
                post.numberLike += 1;
                post.popularity += 1;
            }
        };

        $scope.incrementDisLike = function(post) {
            if (post.disLiked === false) {
                post.disLiked = true;
                likeAndDislike.dislikePost(post.id);
                post.numberDisLike += 1;
                post.popularity += 1;
            }
        };
}
]);

    
/* topic controller */
discussionBoardControllers.controller('PostsCtrl', [
'$scope',
'idPromise',
'reply',
'likeAndDislike',
'authentication',
'$state',
'$window',
function($scope, idPromise, reply, likeAndDislike, authentication, $state,
        $window) {
        //promise of all replies to a topic
        var promise = reply.replyPromise(idPromise)
            .then(function(data) {
                $scope.post = data;
            });

        $scope.addComment = function() {
            if ($scope.content === '') {
                return;
            }
            reply.replyPost(idPromise, $scope.content)
                .then(function(status) {
                    if(status === 200) {
                        $state.go($state.current,{},{reload: true});
                    }
                    // var newUser = {};
                    // newUser.name = authentication.getUserInfo();
                    // $scope.post.listReply.push({
                    //     content: $scope.content,
                    //     numberLike: 0,
                    //     numberDisLike: 0,
                    //     popularity: 0,
                    //     listReply: [],
                    //     id: idPromise,
                    //     createdUser: newUser
                    // });
                    // $scope.post.popularity += 2;
                    // $scope.content = '';
                    //console.log($scope.post.popularity);
                });
        };

        $scope.incrementLike = function(comment) {
            if (comment.liked === false) {
                comment.liked = true;
                likeAndDislike.likePost(comment.id);
                comment.numberLike += 1;
                comment.popularity += 1;
            }
        };

        $scope.incrementDisLike = function(comment) {
            if (comment.disLiked === false) {
                comment.disLiked = true;
                likeAndDislike.dislikePost(comment.id);
                comment.numberDisLike += 1;
                comment.popularity += 1;
            }
        };
}]);
'use strict';

/*Services*/

var discussionBoardServices = angular.module('discussionBoardServices', []);


/*reply service*/
discussionBoardServices.factory('reply', ['$q', '$http', function($q, $http) {
    var RepliesToPost = {
        replyPromise: function(id) {
            var deferred = $q.defer();
            $http.get('/visa/topic?id=' + id)
                .success(function(data) {
                    deferred.resolve(data);
                })
                .error(function(reason) {
                    deferred.reject(reason);
                });
            return deferred.promise;
        },
        replyPost: function(id, content) {
            var req = {
                method: 'POST',
                url: '/visa/reply?id=' + id +
                    '&content=' + content,
                headers: {
                    'Content-Type': "application/json"
                },
                //data: body
            };
            var deferred = $q.defer();
            $http(req)
                .success(function(data, status, headers,
                    config) {
                    deferred.resolve(status);
                })
                .error(function(data, status, headers,
                    config) {
                    deferred.reject(status);
                });
            return deferred.promise;
        }
    };

    return RepliesToPost;
}]);


/*post topic service*/
discussionBoardServices.factory('topics', ['$http', '$q', function($http, $q) {
    var Topics = {
        topicsPromise: function() {

            var deferred = $q.defer();
            $http.get('/visa/topic')
                .success(function(data) {
                    deferred.resolve(data);
                })
                .error(function(reason) {
                    deferred.reject(reason);
                });
            return deferred.promise;
        },
        topicsPost: function(content, title) {
            var req = {
                method: 'POST',
                url: '/visa/topic?content=' + content +
                    '&title=' + title,
                headers: {
                    'Content-Type': "application/json"
                },
                //data: body  
            };
            var deferred = $q.defer();
            $http(req)
                .success(function(data, status, headers,
                    config) {
                    deferred.resolve(status);
                })
                .error(function(data, status, headers,
                    config) {
                    deferred.reject(status);
                });
            return deferred.promise;
        }
    };
    return Topics;
}]);


/*authentication service*/
discussionBoardServices.factory('authentication', ['$http', '$q', '$window',
    function($http, $q, $window) {
        var accessAuth;
        var userInfo;
        var auth = {
            login: function(username, password) {
                var deferred = $q.defer();
                var str = '/visa/login?username=' + username +
                    '&password=' + password;
                $http({
                        method: 'POST',
                        url: str
                    })
                    .success(function(data, status, headers,
                        config) {
                        var accessAuth = {
                            login: 'true'
                        };
                        //after login the working browser tab will store access authentication
                        $window.localStorage["accessAuth"] =
                            JSON.stringify(accessAuth);
                        deferred.resolve(accessAuth);
                    })
                    .error(function(data, status, headers,
                        config) {
                        alert('login failed');
                        deferred.reject(status);
                    });
                return deferred.promise;
            },
            logout: function() {
                var deferred = $q.defer();
                $http({
                        method: 'POST',
                        url: '/visa/logout'
                    })
                    .success(function() {
                        $window.localStorage["accessAuth"] = null;
                        accessAuth = null;
                        deferred.resolve(accessAuth);
                    })
                    .error(function() {
                        alert('logout failed');
                    });
                return deferred.promise;
            },
            getAccessAuth: function() {
                return accessAuth;
            },
            setUserInfo: function(userInfo) {
                this.userInfo = userInfo;
            },
            getUserInfo: function() {
                return userInfo;
            }
        }
        return auth;
}]);


/*like and dislike service*/
discussionBoardServices.factory('likeAndDislike', ['$http', function($http) {
    var updateMethod = {
        likePost: function(id) {
            $http({
                    method: 'POST',
                    url: '/visa/like?id=' + id
                })
                .success(function(status) {})
                .error(function(status) {});
        },
        dislikePost: function(id) {
            $http({
                    method: 'POST',
                    url: '/visa/dislike?id=' + id
                })
                .success(function(status) {})
                .error(function(status) {});
        }
    };
    return updateMethod;
}]);


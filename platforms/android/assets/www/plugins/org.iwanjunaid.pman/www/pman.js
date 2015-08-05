cordova.define("org.iwanjunaid.pman.pman", function(require, exports, module) { module.exports = {
    uninstall: function (packageName, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "PMan", "uninstall", [{
            "packageName": packageName
        }]);
    },
    query: function (packageName, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "PMan", "query", [{
            "packageName": packageName
        }]);
    }
};

});

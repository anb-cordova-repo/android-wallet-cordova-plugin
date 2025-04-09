var exec = require('cordova/exec');
exports.checkEligibility = function (success, error) {
    exec(success, error, 'GoogleProv', 'checkEligibility');
};
exports.getActiveWalletId = function (success, error) {
    exec(success, error, 'GoogleProv', 'getActiveWalletId');
};
exports.getStableHardwareId = function (success, error) {
    exec(success, error,'GoogleProv', 'getStableHardwareId');
};
exports.getEnvironment = function (success, error) {
    exec(success, error,'GoogleProv', 'getEnvironment');
};
exports.registerDataChangedListener = function (success, error) {
    exec(success, error, 'GoogleProv', 'registerDataChangedListener');
};
exports.pushCardTokenize = function (success, error, args) {
    exec(success, error, 'GoogleProv', 'pushCardTokenize', args);
};
exports.pushCobadgeCardTokenize = function (success, error, args) {
    exec(success, error, 'GoogleProv', 'pushCobadgeCardTokenize', args);
};
exports.viewCardToken = function (success, error, args) {
    exec(success, error, 'GoogleProv', 'viewCardToken', args);
};
exports.isCardTokenized = function (success, error, args) {
    exec(success, error, 'GoogleProv', 'isCardTokenized', args);
};
exports.getCardTokensList = function(success, error) {
    exec(success, error, 'GoogleProv', 'getCardTokensList');
};
exports.getCardTokenStatus = function(success, error, args) {
    exec(success, error, 'GoogleProv', 'getCardTokenStatus', args);
};
exports.createCardTokenizeRequest = function(success, error, args) {
    exec(success, error, 'GoogleProv', 'createCardTokenizeRequest', args);
};
exports.createCobadgeCardTokenizeRequest = function(success, error, args) {
    exec(success, error, 'GoogleProv', 'createCobadgeCardTokenizeRequest', args);
};
exports.pushTokenizeResponse = function(success, error, args) {
    exec(success, error, 'GoogleProv', 'pushTokenizeResponse', args);
};
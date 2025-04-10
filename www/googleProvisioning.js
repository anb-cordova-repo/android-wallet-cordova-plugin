var exec = require('cordova/exec');
var PLUGIN_NAME = "GoogleProv";

exports.checkEligibility = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'checkEligibility');
};
exports.getActiveWalletId = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'getActiveWalletId');
};
exports.getStableHardwareId = function (success, error) {
    exec(success, error,PLUGIN_NAME, 'getStableHardwareId');
};
exports.getEnvironment = function (success, error) {
    exec(success, error,PLUGIN_NAME, 'getEnvironment');
};
exports.registerDataChangedListener = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'registerDataChangedListener');
};
exports.pushCardTokenize = function (success, error, args) {
    exec(success, error, PLUGIN_NAME, 'pushCardTokenize', args);
};
exports.pushCobadgeCardTokenize = function (success, error, args) {
    exec(success, error, PLUGIN_NAME, 'pushCobadgeCardTokenize', args);
};
exports.viewCardToken = function (success, error, args) {
    exec(success, error, PLUGIN_NAME, 'viewCardToken', args);
};
exports.isCardTokenized = function (success, error, args) {
    exec(success, error, PLUGIN_NAME, 'isCardTokenized', args);
};
exports.getCardTokensList = function(success, error) {
    exec(success, error, PLUGIN_NAME, 'getCardTokensList');
};
exports.getCardTokenStatus = function(success, error, args) {
    exec(success, error, PLUGIN_NAME, 'getCardTokenStatus', args);
};
exports.createCardTokenizeRequest = function(success, error, args) {
    exec(success, error, PLUGIN_NAME, 'createCardTokenizeRequest', args);
};
exports.createCobadgeCardTokenizeRequest = function(success, error, args) {
    exec(success, error, PLUGIN_NAME, 'createCobadgeCardTokenizeRequest', args);
};
exports.pushTokenizeResponse = function(success, error, args) {
    exec(success, error, PLUGIN_NAME, 'pushTokenizeResponse', args);
};
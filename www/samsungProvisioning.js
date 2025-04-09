var exec = require('cordova/exec');
exports.checkEligibility = function (success, error) {
    exec(success, error, 'SamsungProv', 'checkEligibility');
};
exports.activateSamsungPay = function (success, error) {
    exec(success, error,'SamsungProv', 'activateSamsungPay');
};
exports.goToUpdatePage = function (success, error) {
    exec(success, error,'SamsungProv', 'goToUpdatePage');
};
exports.getVisaWalletInfo = function (success, error) {
    exec(success, error, 'SamsungProv', 'getVisaWalletInfo');
};
exports.addCard = function (success, error, args) {
    exec(success, error, 'SamsungProv', 'addCard', args);
};
exports.addCoBadgeCard = function (success, error, args) {
    exec(success, error, 'SamsungProv', 'addCoBadgeCard', args);
};
exports.getAllCards = function(success, error) {
    exec(success, error, 'SamsungProv', 'getAllCards');
};
exports.getCardById = function(success, error, args) {
    exec(success, error, 'SamsungProv', 'getCardById', args);
};
exports.verifyCardIdv = function(success, error, args) {
    exec(success, error, 'SamsungProv', 'verifyCardIdv', args);
};
exports.registerAddCardProgressListener = function (success, error) {
    exec(success, error, 'SamsungProv', 'registerAddCardProgressListener');
};

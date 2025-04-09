var exec = require("cordova/exec");
var PLUGIN_NAME = "SamsungProv";

exports.checkEligibility = function (success, error) {
  exec(success, error, PLUGIN_NAME, "checkEligibility");
};

exports.activateSamsungPay = function (success, error) {
  exec(success, error, PLUGIN_NAME, "activateSamsungPay");
};

exports.goToUpdatePage = function (success, error) {
  exec(success, error, PLUGIN_NAME, "goToUpdatePage");
};

exports.getVisaWalletInfo = function (success, error) {
  exec(success, error, PLUGIN_NAME, "getVisaWalletInfo");
};

exports.addCard = function (success, error, payload) {
  exec(success, error, PLUGIN_NAME, "addCard", payload);
};

exports.getAllCards = function (success, error) {
  exec(success, error, PLUGIN_NAME, "getAllCards");
};

exports.getCardById = function (success, error, cardId) {
  exec(success, error, PLUGIN_NAME, "getCardById", [cardId]);
};

exports.setServiceId = function (success, error, serviceId) {
  exec(success, error, PLUGIN_NAME, "setServiceId", [serviceId]);
};

exports.getServiceId = function (success, error) {
  exec(success, error, PLUGIN_NAME, "getServiceId");
};

exports.init = function (success, error) {
  exec(success, error, PLUGIN_NAME, "init");
};

exports.verifyCardIdv = function (success, error, args) {
  exec(success, error, PLUGIN_NAME, "verifyCardIdv", args);
};

exports.addCoBadgeCard = function (success, error, args) {
  exec(success, error, PLUGIN_NAME, "addCoBadgeCard", args);
};

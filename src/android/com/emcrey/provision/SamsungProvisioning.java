package com.emcrey.provision;

import android.text.TextUtils;

import com.emcrey.payment.ISamProvSDK;
import com.emcrey.payment.SamProvSDK;
import com.emcrey.payment.card.CardInfo;
import com.emcrey.payment.card.CardVerificationType;
import com.emcrey.payment.card.Result;
import com.emcrey.payment.card.Scheme;
import com.emcrey.payment.listeners.CardResultListener;
import com.emcrey.payment.listeners.GetCardsListener;
import com.emcrey.payment.listeners.GetVisaInfoListener;
import com.emcrey.payment.listeners.ResultListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class SamsungProvisioning extends CordovaPlugin {

    private CallbackContext callbackContext;
    private ISamProvSDK iSamProvSDK;
    private final Gson gson = new Gson();
    private String ServiceId;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        switch (action) {
            case "checkEligibility":
                this.checkEligibility();
                return true;
            case "addCard":
                this.addCard(args);
                return true;
            case "activateSamsungPay":
                this.activateSamsungPay();
                return true;
            case "goToUpdatePage":
                this.goToUpdatePage();
                return true;
            case "getVisaWalletInfo":
                this.getVisaWalletInfo();
                return true;
            case "getAllCards":
                this.getAllCards();
                return true;
            case "getCardById":
                this.getCardById(args);
                return true;
            case "setServiceId":
                this.setServiceId(args);
                return true;
            case "getServiceId":
                this.getServiceId();
                return true;
            case "init":
                this.init();
                return true;
            case "verifyCardIdv":
                this.verifyCardIdv(args);
                return true;
            case "addCoBadgeCard":
                this.addCoBadgeCard(args);
                return true;
        }
        return false;
    }

    private void init() {
        super.initialize(this.cordova, this.webView);
        if (!TextUtils.isEmpty(ServiceId)) {
            this.iSamProvSDK = SamProvSDK.initSamsungPrevisioning(this.cordova.getActivity(), ServiceId);
            callbackContext.success("Samsung SDK initialized with Service ID: " + ServiceId);
        } else {
            callbackContext.error("Service ID not set");
        }
    }

    public void checkEligibility() {
        if (iSamProvSDK == null) {
            callbackContext.error("Samsung SDK not initialized");
            return;
        }

        iSamProvSDK.checkEligibility((available, result) -> {
            JSONObject object = new JSONObject();
            try {
                object.put("available", available);
                object.put("result", gson.toJson(result));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            callbackContext.success(object);
        });
    }

    private void setServiceId(JSONArray args) throws JSONException {
        String newServiceId = args.getString(0);
        if (!TextUtils.isEmpty(newServiceId)) {
            this.ServiceId = newServiceId;
            this.iSamProvSDK = SamProvSDK.initSamsungPrevisioning(this.cordova.getActivity(), ServiceId);
            callbackContext.success("Service ID set to " + newServiceId);
        } else {
            callbackContext.error("Service ID is empty");
        }
    }

    private void getServiceId() {
        if (this.ServiceId == null) {
            callbackContext.error("Service ID is not set");
        }

        callbackContext.success(this.ServiceId);
    }

    private void addCard(JSONArray args) throws JSONException {
        String payload = (String) args.get(0);
        Scheme scheme = Scheme.valueOf((String) args.get(1));
        iSamProvSDK.addCard(payload, scheme, new CardResultListener() {
            @Override
            public void onSuccess(CardInfo cardInfo) {
                String card = gson.toJson(cardInfo);
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("cardInfo", card);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(Result result) {

                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("result", gson.toJson(result));
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    private void getVisaWalletInfo() {
        iSamProvSDK.getVisaWalletInfo(new GetVisaInfoListener() {
            @Override
            public void onSuccess(String clientDeviceId, String clientWalletAccountId) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("clientDeviceId", clientDeviceId);
                    object.put("clientWalletAccountId", clientWalletAccountId);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(Result result) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("result", gson.toJson(result));
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    private void getAllCards() {
        iSamProvSDK.getAllCards(new GetCardsListener() {
            @Override
            public void onSuccess(List<CardInfo> list) {
                Type listType = new TypeToken<List<CardInfo>>() {
                }.getType();
                String card = gson.toJson(list, listType);
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("cards", card);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(Result result) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("result", gson.toJson(result));
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    private void getCardById(JSONArray args) throws JSONException {
        String cardId = (String) args.get(0);
        iSamProvSDK.getCardById(cardId, new CardResultListener() {
            @Override
            public void onSuccess(CardInfo cardInfo) {
                String card = gson.toJson(cardInfo);
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("cardInfo", card);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(Result result) {

                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("result", gson.toJson(result));
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    private void verifyCardIdv(JSONArray args) throws JSONException {
        String cardId = (String) args.get(0);
        String otp = (String) args.get(1);
        String vType = (String) args.get(2);
        CardVerificationType type = CardVerificationType.valueOf(vType);
        iSamProvSDK.verifyCardIdv(cardId, otp, type, new ResultListener() {
            @Override
            public void result(boolean success, Result result) {
                JSONObject object = new JSONObject();
                try {
                    object.put("success", success);
                    object.put("result", gson.toJson(result));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }
        });
    }

    private void addCoBadgeCard(JSONArray args) throws JSONException {
        String primaryPayload = (String) args.get(0);
        Scheme primaryScheme = Scheme.valueOf((String) args.get(1));
        String secondaryPayload = (String) args.get(2);
        Scheme secondaryScheme = Scheme.valueOf((String) args.get(3));
        iSamProvSDK.addCoBadgeCard(primaryPayload, primaryScheme, secondaryPayload, secondaryScheme,
                new CardResultListener() {
                    @Override
                    public void onSuccess(CardInfo cardInfo) {
                        String card = gson.toJson(cardInfo);
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("cardInfo", card);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(Result result) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("result", gson.toJson(result));
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }

    private void activateSamsungPay() {
        iSamProvSDK.activateSamsungPay();
    }

    private void goToUpdatePage() {
        iSamProvSDK.goToUpdatePage();
    }
}
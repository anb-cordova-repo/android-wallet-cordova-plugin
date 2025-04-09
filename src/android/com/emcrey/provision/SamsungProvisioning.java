package com.emcrey.provision;

import android.content.Context;
import android.text.TextUtils;

import com.emcrey.payment.ISamProvSDK;
import com.emcrey.payment.SamProvSDK;
import com.emcrey.payment.card.CardInfo;
import com.emcrey.payment.card.CardVerificationType;
import com.emcrey.payment.card.Result;
import com.emcrey.payment.card.Scheme;
import com.emcrey.payment.listeners.AddCardResultListener;
import com.emcrey.payment.listeners.CardResultListener;
import com.emcrey.payment.listeners.GetCardsListener;
import com.emcrey.payment.listeners.GetVisaInfoListener;
import com.emcrey.payment.listeners.ResultListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

public class SamsungProvisioning extends CordovaPlugin {

    private CallbackContext callbackContext, addCardProgressListener;
    private ISamProvSDK iSamProvSDK;
    private final Gson gson = new Gson();
    private String ServiceId = "7c840709b99447699fd5db";

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        String serviceId = (String) getBuildConfigValue(cordova.getContext(), "gradle_server_id");
        if (!TextUtils.isEmpty(serviceId)) {
            ServiceId = serviceId;
        }
        this.iSamProvSDK = SamProvSDK.initSamsungPrevisioning(cordova.getActivity(), ServiceId);
    }

    public static Object getBuildConfigValue(Context context, String fieldName) {
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals("checkEligibility")) {
            this.checkEligibility();
            return true;
        }
        if (action.equals("addCard")) {
            this.addCard(args);
            return true;
        }
        if (action.equals("addCoBadgeCard")) {
            this.addCoBadgeCard(args);
            return true;
        }
        if (action.equals("activateSamsungPay")) {
            this.activateSamsungPay();
            return true;
        }
        if (action.equals("goToUpdatePage")) {
            this.goToUpdatePage();
            return true;
        }
        if (action.equals("getVisaWalletInfo")) {
            this.getVisaWalletInfo();
            return true;
        }
        if (action.equals("getAllCards")) {
            this.getAllCards();
            return true;
        }
        if (action.equals("getCardById")) {
            this.getCardById(args);
            return true;
        }
        if (action.equals("verifyCardIdv")) {
            this.verifyCardIdv(args);
            return true;
        }
        if (action.equals("registerAddCardProgressListener")) {
            addCardProgressListener = callbackContext;
            PluginResult r = new PluginResult(PluginResult.Status.NO_RESULT);
            r.setKeepCallback(true);
            addCardProgressListener.sendPluginResult(r);
            return true;
        }
        return false;
    }

    public void checkEligibility() {
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

    private void addCard(JSONArray args) throws JSONException {
        String payload = (String) args.get(0);
        Scheme scheme = Scheme.valueOf((String) args.get(1));
        iSamProvSDK.addCard(payload, scheme, new AddCardResultListener() {

            @Override
            public void onProgress(int currentCount, int totalCount) {
                sendAddCardProgress(currentCount, totalCount);
            }

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

    private void addCoBadgeCard(JSONArray args) throws JSONException {
        String primaryPayload = (String) args.get(0);
        Scheme primaryScheme = Scheme.valueOf((String) args.get(1));
        String secondaryPayload = (String) args.get(2);
        Scheme secomdaryScheme = Scheme.valueOf((String) args.get(3));
        iSamProvSDK.addCoBadgeCard(primaryPayload, primaryScheme, secondaryPayload, secomdaryScheme, new AddCardResultListener() {
            @Override
            public void onProgress(int currentCount, int totalCount) {
                sendAddCardProgress(currentCount, totalCount);
            }

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
        String vtype = (String) args.get(2);
        CardVerificationType type = CardVerificationType.valueOf(vtype);
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

    private void activateSamsungPay() {
        iSamProvSDK.activateSamsungPay();
    }

    private void goToUpdatePage() {
        iSamProvSDK.goToUpdatePage();
    }


    private void sendAddCardProgress(int currentCount, int totalCount) {
        if (addCardProgressListener != null) {
            JSONObject object = new JSONObject();
            try {
                object.put("currentCount", currentCount);
                object.put("totalCount", totalCount);
                PluginResult r = new PluginResult(PluginResult.Status.OK, object);
                r.setKeepCallback(true);
                addCardProgressListener.sendPluginResult(r);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onReset() {
    }
}

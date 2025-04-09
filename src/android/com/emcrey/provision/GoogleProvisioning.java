package com.emcrey.provision;

import android.content.Intent;
import android.util.Log;

import com.emcrey.payment.GoogleProvSDK;
import com.emcrey.payment.IGoogleProvSDK;
import com.emcrey.payment.card.Address;
import com.emcrey.payment.card.CardNetwork;
import com.emcrey.payment.card.CardToken;
import com.emcrey.payment.card.PaymentRequest;
import com.emcrey.payment.card.PaymentResponse;
import com.emcrey.payment.card.TokenProvider;
import com.emcrey.payment.card.TokenStatus;
import com.emcrey.payment.card.TokenizeResult;
import com.emcrey.payment.listeners.ResultValueListener;
import com.emcrey.payment.listeners.TokenStatusListener;
import com.emcrey.payment.listeners.TokenizeListener;
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

import java.lang.reflect.Type;
import java.util.List;

public class GoogleProvisioning extends CordovaPlugin {
    private CallbackContext callbackContext, dataChangeCallbackContext;
    IGoogleProvSDK iGoogleProvSDK;
    private final Gson gson = new Gson();


    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        iGoogleProvSDK = GoogleProvSDK.initGooglePrevisioning(this.cordova.getActivity());
        iGoogleProvSDK.registerDataChangedListener(() -> {
            if (dataChangeCallbackContext != null) {
                PluginResult r = new PluginResult(PluginResult.Status.OK, "data has changed ");
                r.setKeepCallback(true);
                dataChangeCallbackContext.sendPluginResult(r);
            }
        });
    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals("getActiveWalletId")) {
            this.getActiveWalletId();
            return true;
        }
        if (action.equals("checkEligibility")) {
            this.checkEligibility();
            return true;
        }
        if (action.equals("getStableHardwareId")) {
            this.getStableHardwareId();
            return true;
        }
        if (action.equals("registerDataChangedListener")) {
            dataChangeCallbackContext = callbackContext;
            PluginResult r = new PluginResult(PluginResult.Status.NO_RESULT);
            r.setKeepCallback(true);
            dataChangeCallbackContext.sendPluginResult(r);
            return true;
        }
        if (action.equals("getEnvironment")) {
            this.getEnvironment();
            return true;
        }
        if (action.equals("pushCardTokenize")) {
            this.pushCardTokenize(args.getString(0), CardNetwork.valueOf(args.getString(1)), TokenProvider.valueOf(args.getString(2)),
                    args.getString(3), args.getString(4), args.getString(5));
            return true;
        }
        if (action.equals("pushCobadgeCardTokenize")) {
            this.pushCobadgeCardTokenize(args.getString(0), CardNetwork.valueOf(args.getString(1)), TokenProvider.valueOf(args.getString(2)),
                    args.getString(3), CardNetwork.valueOf(args.getString(4)), TokenProvider.valueOf(args.getString(5)),
                    args.getString(6), args.getString(7), args.getString(8));
            return true;
        }
        if (action.equals("viewCardToken")) {
            this.viewCardToken(args.getString(0), TokenProvider.valueOf(args.getString(1)));
            return true;
        }
        if (action.equals("isCardTokenized")) {
            this.isCardTokenized(args.getString(0), CardNetwork.valueOf(args.getString(1)), TokenProvider.valueOf(args.getString(2)));
            return true;
        }
        if (action.equals("getCardTokensList")) {
            this.getCardTokensList();
            return true;
        }
        if (action.equals("getCardTokenStatus")) {
            this.getCardTokenStatus(args.getString(0), TokenProvider.valueOf(args.getString(1)));
            return true;
        }
        if (action.equals("createCardTokenizeRequest")) {
            this.createCardTokenizeRequest(CardNetwork.valueOf(args.getString(0)), TokenProvider.valueOf(args.getString(1)), args.getString(2), args.getString(3), args.getString(4));
            return true;
        }
        if (action.equals("createCobadgeCardTokenizeRequest")) {
            this.createCobadgeCardTokenizeRequest(CardNetwork.valueOf(args.getString(0)), TokenProvider.valueOf(args.getString(1)), CardNetwork.valueOf(args.getString(2)), TokenProvider.valueOf(args.getString(3)), args.getString(4), args.getString(5), args.getString(6));
            return true;
        }
        if (action.equals("pushTokenizeResponse")) {
            this.pushTokenizeResponse(args.getString(0));
            return true;
        }
        return false;
    }

    public void checkEligibility() {
        iGoogleProvSDK.checkEligibility(new ResultValueListener<Boolean>() {
            @Override
            public void onSuccess(Boolean available) {
                JSONObject object = new JSONObject();
                try {
                    object.put("available", available);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getActiveWalletId() {
        iGoogleProvSDK.getActiveWalletId(new ResultValueListener<String>() {
            @Override
            public void onSuccess(String activeWalletId) {
                JSONObject object = new JSONObject();
                try {
                    object.put("activeWalletId", activeWalletId);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getStableHardwareId() {
        iGoogleProvSDK.getStableHardwareId(new ResultValueListener<String>() {
            @Override
            public void onSuccess(String stableHardwareId) {
                JSONObject object = new JSONObject();
                try {
                    object.put("stableHardwareId", stableHardwareId);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getEnvironment() {
        iGoogleProvSDK.getEnvironment(new ResultValueListener<String>() {
            @Override
            public void onSuccess(String environment) {
                JSONObject object = new JSONObject();
                try {
                    object.put("environment", environment);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void pushCardTokenize(String payloadBase64, CardNetwork network, TokenProvider provider,
                                 String googleOPCPayload, String displayName, String last4Pan) {
        iGoogleProvSDK.pushCardTokenize(payloadBase64, network, provider, googleOPCPayload, displayName, last4Pan,
                new TokenizeListener() {
                    @Override
                    public void onComplete(TokenizeResult tokenizeResult) {
                        Type listType = new TypeToken<TokenizeResult>() {
                        }.getType();
                        JSONObject object = new JSONObject();
                        try {
                            object.put("TokenizeResult", tokenizeResult);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("code", code);
                            object.put("message", message);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }


    public void pushCobadgeCardTokenize(String primaryPayloadBase64, CardNetwork primaryNetwork, TokenProvider primaryProvider,
                                        String auxiliaryPayloadBase64, CardNetwork auxiliaryNetwork, TokenProvider auxiliaryProvider,
                                        String googleOPCPayload, String displayName, String last4Pan) {
        iGoogleProvSDK.pushCobadgeCardTokenize(primaryPayloadBase64, primaryNetwork, primaryProvider,
                auxiliaryPayloadBase64, auxiliaryNetwork, auxiliaryProvider, googleOPCPayload, displayName, last4Pan,
                new TokenizeListener() {
                    @Override
                    public void onComplete(TokenizeResult tokenizeResult) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("TokenizeResult", gson.toJson(tokenizeResult));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("code", code);
                            object.put("message", message);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }

    public void viewCardToken(String issuerTokenId, TokenProvider provider) {
        iGoogleProvSDK.viewCardToken(issuerTokenId, provider, new ResultValueListener<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                JSONObject object = new JSONObject();
                try {
                    object.put("success", success);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void isCardTokenized(String last4Pan, CardNetwork network, TokenProvider provider) {
        iGoogleProvSDK.isCardTokenized(last4Pan, network, provider, new ResultValueListener<Boolean>() {
            @Override
            public void onSuccess(Boolean isCardTokenized) {
                JSONObject object = new JSONObject();
                try {
                    object.put("isCardTokenized", isCardTokenized);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getCardTokensList() {
        iGoogleProvSDK.getCardTokensList(new ResultValueListener<List<CardToken>>() {
            @Override
            public void onSuccess(List<CardToken> cards) {
                JSONObject object = new JSONObject();
                try {
                    Type listType = new TypeToken<List<CardToken>>() {
                    }.getType();
                    String serialize = gson.toJson(cards, listType);
                    object.put("cardTokens", serialize);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getCardTokenStatus(String issuerTokenId, TokenProvider provider) {
        iGoogleProvSDK.getCardTokenStatus(issuerTokenId, provider, new TokenStatusListener() {
            @Override
            public void onSuccess(TokenStatus tokenStatus, Boolean isSelected) {
                JSONObject object = new JSONObject();
                try {
                    object.put("tokenStatus", tokenStatus.name());
                    object.put("isSelected", isSelected);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }


    public void createCardTokenizeRequest(CardNetwork network, TokenProvider provider,
                                          String displayName, String last4Pan, String address) {
        iGoogleProvSDK.createCardTokenizeRequest(network, provider, displayName, last4Pan, parseAddress(address),
                new ResultValueListener<PaymentRequest>() {

                    @Override
                    public void onSuccess(PaymentRequest request) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("PaymentRequest", gson.toJson(request));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("code", code);
                            object.put("message", message);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }

    public void createCobadgeCardTokenizeRequest(CardNetwork primaryNetowrk, TokenProvider primaryProvider,
                                                 CardNetwork auxNetowrk, TokenProvider auxProvider,
                                                 String displayName, String last4Pan, String address) {
        iGoogleProvSDK.createCobadgeCardTokenizeRequest(primaryNetowrk, primaryProvider, auxNetowrk, auxProvider, displayName, last4Pan, parseAddress(address), false,
                new ResultValueListener<PaymentRequest>() {

                    @Override
                    public void onSuccess(PaymentRequest request) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("PaymentRequest", gson.toJson(request));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("code", code);
                            object.put("message", message);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }

    public void pushTokenizeResponse(String paymentResponse) {
        PaymentResponse response = parsePaymentResponse(paymentResponse);
        iGoogleProvSDK.pushTokenizeResponse(response,
                new TokenizeListener() {

                    @Override
                    public void onComplete(TokenizeResult tokenizeResult) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("TokenizeResult", gson.toJson(tokenizeResult));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("code", code);
                            object.put("message", message);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        iGoogleProvSDK.handleTokenizationResult(requestCode, resultCode, intent);
    }

    private Address parseAddress(String addr) {
        Address address = null;
        try {
            Log.i("googleProvisioning", "parseAddress: " + addr);
            address = new Gson().fromJson(addr, Address.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    private PaymentResponse parsePaymentResponse(String paymentResponse) {
        PaymentResponse response = null;
        try {
            response = new Gson().fromJson(paymentResponse, PaymentResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


}

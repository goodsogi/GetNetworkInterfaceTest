/**
 * @file MobileMainActivity.java
 */
package com.commax.onvif_device_manager;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.commax.onvif_device_manager.content_provider.ContentProviderConstants;
import com.commax.onvif_device_manager.content_provider.ContentProviderManager;
import com.commax.onvif_device_manager.device.DeviceDeleteListener;
import com.commax.onvif_device_manager.device.DeviceInfo;
import com.commax.onvif_device_manager.device.DeviceInfoPopup;
import com.commax.onvif_device_manager.device.IPDevice;
import com.commax.onvif_device_manager.device.IPDeviceListAdapter;
import com.commax.onvif_device_manager.device.NewIPAddressManager;
import com.commax.onvif_device_manager.network.NetworkOnvifRequester;
import com.commax.onvif_device_manager.network.OnvifProbe;
import com.commax.onvif_device_manager.uitls.DeviceInfoConfirmListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.onvif.ver10.schema.nativeParcel.ProbeMatch;

import java.util.ArrayList;
import java.util.List;

/**
 * OnVif Discovery로 IP Camera와 CCTV 찾는 화면
 */
public class MobileMainActivity extends FragmentActivity  {


    private static final String HIKVISION_IP_ADDRESS = "192.168.0.254";
    //SADP에는 포트번호가 8000이나 로그캣을 보니 80인듯
    private static final int HIKVISION_PORT = 80;
    private static final String HIKVISION_DEFAULT_ID = "admin";
    private static final String HIKVISION_DEFAULT_PASSWORD = "12345";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getNetworkInterface();

    }



    /**
     *  GetNetworkInterface 호출
     * @return
     */
    private void getNetworkInterface() {



        NetworkOnvifRequester requester = new NetworkOnvifRequester(HIKVISION_IP_ADDRESS, HIKVISION_PORT, HIKVISION_DEFAULT_ID, HIKVISION_DEFAULT_PASSWORD);
        int result = requester.createDeviceManagementAuthHeader();
        if (result == NetworkOnvifRequester.ERROR_SOCKET_TIMEOUT) {
            return;
        }

        if ((result = requester.getNetworkInterface()) > -1) {

            Toast.makeText(this, "맥어드레스: " + requester.mGetNetworkResponse.mNetworkInterfacesInfoHwAddress, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "GetNetworkInterface 사용 불가", Toast.LENGTH_SHORT).show();
        }

    }




}

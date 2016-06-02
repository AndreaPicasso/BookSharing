package com.example.simone.booksharing;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SeekBar;

import java.nio.charset.Charset;


public class ChatFragment extends android.app.Fragment {
    private WebView web;

    public void onCreate(Bundle savedInstanceState) {

        CheckConnection.isOnline(getActivity());
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        web=(WebView)view.findViewById(R.id.chatwv);

        web.getSettings().setJavaScriptEnabled(true);


        web.setWebViewClient(new WebViewClient());
        String postData = "pswAccesso="+UnigeServerConnection.PSW_ACCESSO+"&email=a@a.it";


        web.postUrl("http://webdev.dibris.unige.it/~S3940125/chatapp.php", postData.getBytes(Charset.forName("utf-8")));
        //web.loadUrl("http://webdev.dibris.unige.it/~S3940125/chatapp.php");
        return view;

    }
}

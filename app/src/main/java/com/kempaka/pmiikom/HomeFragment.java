package com.kempaka.pmiikom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kempaka.pmiikom.landasan.Aswaja;
import com.kempaka.pmiikom.landasan.Ndp;
import com.kempaka.pmiikom.landasan.sejarah_pmii;


public class HomeFragment extends Fragment {

    private CardView sejarah,ndp,aswajaa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveIntanceState){

        View rootView = inflater.inflate( R.layout.fragmet_home,container,false );

        sejarah = (CardView) rootView.findViewById(R.id.idbtnActivitySejarah);
        ndp = (CardView) rootView.findViewById( R.id.idbtnActivityNdp );
        aswajaa = (CardView) rootView.findViewById( R.id.idbtnActivityAswaja );

        sejarah.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                update();
            }
        });
        ndp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ndp_pmii();
            }
        } );
        aswajaa.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aswajaa_pmii();
            }
        } );
        return rootView;
    }

    private void aswajaa_pmii() {
        Intent intent = new Intent(getActivity(), Aswaja.class);
        startActivity(intent);
    }

    private void ndp_pmii() {
        Intent intent = new Intent(getActivity(), Ndp.class);
        startActivity(intent);
    }

    private void update() {
        Intent intent = new Intent(getActivity(), sejarah_pmii.class);
        startActivity(intent);

    }
}

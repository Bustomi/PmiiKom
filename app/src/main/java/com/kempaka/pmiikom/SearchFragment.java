package com.kempaka.pmiikom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kempaka.pmiikom.database.agenda_pmii;
import com.kempaka.pmiikom.database.karya_pmii;
import com.kempaka.pmiikom.database.upload_data;


public class SearchFragment extends Fragment {

private  CardView btnpendataan,btnAgenda,btnKarya;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveIntanceState){

        View rootView = inflater.inflate( R.layout.activity_search_fragment,container,false );

        btnAgenda = (CardView) rootView.findViewById( R.id.idbtnActivityAgenda );
        btnKarya = (CardView) rootView.findViewById( R.id.idbtnActivityKarya );
        btnpendataan = (CardView) rootView.findViewById(R.id.idbtnActivityPendataan);

        btnpendataan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                update();
                }
        });
        btnAgenda.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agenda();
            }
        } );
        btnKarya.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                karya();
            }
        } );
        return rootView;
        }

    private void karya() {
        Intent intent = new Intent(getActivity(), agenda_pmii.class);
        startActivity(intent);
    }

    private void agenda() {
        Intent intent = new Intent(getActivity(), karya_pmii.class);
        startActivity(intent);
    }

    private void update() {
        Intent intent = new Intent(getActivity(), upload_data.class);
        startActivity(intent);
    }

}



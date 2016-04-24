package com.serk.ShopCompanion;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azap Serkan, ie04114
 * Ce fragment accessible depuis le navigation drawer affiche les magasins dans une carte avec leurs horaires
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    String url = ServerURL.SHOP_GET;
    List<ShopObject> shops = new ArrayList<ShopObject>();
    ProgressDialog dialog;
    private GoogleMap map;
    private Marker marker;

    public MapFragment() {
    }

    /**
     * Appeler lorsque la carte est initialisé
     *
     * @param
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        setUpMap();

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Chargement");
        dialog.setCancelable(true);
        showDialog();
        getShopsAddMArker();
    }

    private void setUpMap() { //Definit les options de la carte

        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(48.8558086, 2.3293788), 15);
        map.animateCamera(cameraUpdate);

        /*map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                //remove previously placed Marker
                if (marker != null) {
                    marker.remove();
                }

                //place marker where user just clicked
                marker = map.addMarker(new MarkerOptions().position(point).title("Mon marqueur")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            }
        });*/
    }

    public void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    public void hideDialog() {
        if (dialog.isShowing())
            dialog.hide();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Récupère les magasins depuis le serveur et créer un marqueur avec une couleur correspondant a la marque et ajoute leurs information dans la fenètre d'information
     */
    public void getShopsAddMArker() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                ShopObject shop = new ShopObject();
                                shop.setId(object.getString("id"));
                                shop.setNom(object.getString("nom"));
                                shop.setMarque(object.getString("marque"));
                                shop.setAdressemag(object.getString("adressemag"));
                                shop.setLatitude(object.getString("latitude"));
                                shop.setLongitude(object.getString("longitude"));
                                shop.setLundi(object.getString("lundi"));
                                shop.setMardi(object.getString("mardi"));
                                shop.setMercredi(object.getString("mercredi"));
                                shop.setJeudi(object.getString("jeudi"));
                                shop.setVendredi(object.getString("vendredi"));
                                shop.setSamedi(object.getString("samedi"));
                                shop.setDimanche(object.getString("dimanche"));

                                shops.add(shop);

                                switch (shop.getMarque()) {
                                    case ("Leclerc"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(16))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("Carrefour"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(224))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("Carrefour Market"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(224))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("Carrefour City"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(224))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("Auchan"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(80))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("G20"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(110))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("Geant Casino"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(55))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("Monoprix"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(270))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                    case ("Framprix"):
                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(35))
                                                .title(shop.getNom())
                                                .snippet(
                                                        shop.getAdressemag() + "\n" +
                                                                "Lundi " + shop.getLundi() +
                                                                "  Mardi " + shop.getMardi() + "\n" +
                                                                "Mercredi " + shop.getMercredi() +
                                                                "  Jeudi " + shop.getJeudi() + "\n" +
                                                                "Vendredi " + shop.getVendredi() +
                                                                "  Samedi " + shop.getSamedi() + "\n" +
                                                                "Dimanche " + shop.getDimanche()
                                                )
                                                .position(new LatLng(
                                                        Double.parseDouble(shop.getLatitude()),
                                                        Double.parseDouble(shop.getLongitude()))));
                                        break;
                                }
                            }
                            hideDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR!!!", error.toString());
            }
        });
        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() { // Créer une fenetre d'information personalisé pour afficher plus de 2 ligne et ainsi ajouter l'adresse et les horaires

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getActivity();

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });


    }
}

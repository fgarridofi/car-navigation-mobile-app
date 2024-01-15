package com.example.formapp.ui

//package com.mapbox.search.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.Manifest
import android.content.Intent
import android.graphics.PorterDuff
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.formapp.R
import com.example.formapp.data.repository.ComponentRepositoryImpl
import com.example.formapp.domain.repository.ComponentRepository
import com.example.formapp.domain.vm.HistorialViewModel
import com.example.formapp.navigation.AppScreens
import com.example.formapp.ui.screens.*
import com.google.android.material.card.MaterialCardView
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.search.autofill.AddressAutofill
import com.mapbox.search.autofill.AddressAutofillOptions
import com.mapbox.search.autofill.AddressAutofillSuggestion
import com.mapbox.search.autofill.Query
import com.mapbox.search.ui.adapter.autofill.AddressAutofillUiAdapter
import com.mapbox.search.ui.view.CommonSearchViewConfiguration
import com.mapbox.search.ui.view.DistanceUnitType
import com.mapbox.search.ui.view.SearchResultsView
import com.mapbox.search.ui.view.place.SearchPlaceBottomSheetView
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity4 : AppCompatActivity() {

    @Inject
    lateinit var componentsRepository: ComponentRepositoryImpl


    private val componentViewModel: ComponentViewModel by viewModels()


    private var showingComposeScreen = false

    private var selectedCoordinate: Point? = null


    private val AVERAGE_SPEED_KMH = 60.0

    private lateinit var addressAutofill: AddressAutofill

    private lateinit var searchResultsView: SearchResultsView
    private lateinit var addressAutofillUiAdapter: AddressAutofillUiAdapter

    private lateinit var queryEditText: EditText


    private lateinit var mapView: MapView
    private lateinit var mapPin: ImageView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var searchPlaceView: SearchPlaceBottomSheetView
    private lateinit var buttonNavigateCard : MaterialCardView
    private lateinit var buttonNavigate : Button

    private lateinit var buttonRecambios : RelativeLayout
    private lateinit var buttonHistorial : RelativeLayout

    private var ignoreNextMapIdleEvent: Boolean = false
    private var ignoreNextQueryTextUpdate: Boolean = false

    private var ignoreNextQueryUpdate = false

    private var direccionOrigen: String? = null
    private var direccionDestino: String? = null
    private var fechaActual: String? = null
    private var distanciaKilometros: Double? = null
    private val historialViewModel: HistorialViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_address_autofill)

        addressAutofill = AddressAutofill.create(getString(R.string.mapbox_access_token))

        queryEditText = findViewById(R.id.query_text)


        mapPin = findViewById(R.id.map_pin)
        val redColor = ContextCompat.getColor(this, R.color.red)
        mapPin.setColorFilter(redColor, PorterDuff.Mode.SRC_IN)


        mapView = findViewById(R.id.map)
        mapboxMap = mapView.getMapboxMap()
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
        mapboxMap.addOnMapIdleListener {
            if (ignoreNextMapIdleEvent) {
                ignoreNextMapIdleEvent = false
                return@addOnMapIdleListener
            }

            val mapCenter = mapboxMap.cameraState.center
            findAddress(mapCenter)
        }



        buttonHistorial= findViewById(R.id.button2)
        buttonHistorial.setOnClickListener {
            showComposeView(AppScreens.HistorialScreen.route)
        }

        buttonRecambios = findViewById(R.id.button1)
        buttonRecambios.setOnClickListener {
            showComposeView(AppScreens.RecambiosScreen.route)
        }

        val direccionSeleccionada = "Direccion seleccionada"

        buttonNavigateCard = findViewById(R.id.card_view)
        buttonNavigate = findViewById(R.id.button_navigate)
        buttonNavigate.setOnClickListener {
            selectedCoordinate?.let { point ->
                // Asigna la fecha actual
                fechaActual = getCurrentDate()

                // Calcula la distancia en kilómetros
                val locationEngine = LocationEngineProvider.getBestLocationEngine(applicationContext)
                locationEngine.lastKnownLocation(this) { origin ->
                    origin?.let {

                        val originPoint = Point.fromLngLat(origin.longitude(), origin.latitude())
                        Log.e("222222222222",originPoint.toString())

                        val distanceMeters = TurfMeasurement.distance(
                            Point.fromLngLat(origin.longitude(), origin.latitude()),
                            point,
                            TurfConstants.UNIT_KILOMETERS
                        )
                        distanciaKilometros = distanceMeters


                        //guardo direccion origen

                        lifecycleScope.launchWhenStarted {
                            val response = addressAutofill.suggestions(originPoint, AddressAutofillOptions())

                            ///inseto
                            val roundedDistance = distanciaKilometros?.toInt() ?: 0
                            updateAllComponentsKilometers(roundedDistance)

                            response.onValue { suggestions ->

                                if (suggestions.isNotEmpty()) {
                                    val address = suggestions.first().result().address
                                    direccionOrigen = listOfNotNull(
                                        address.street,
                                        address.houseNumber,
                                        address.place,
                                    ).joinToString()
                                    Log.e("222222222222",direccionOrigen.toString())
                                    insertHistorial()// inserto historial

                                }
                            }.onError { error ->
                                Log.d("MainActivity4", "Error al obtener la dirección de origen: $error", error)
                            }
                        }


                        // Llama a la función para insertar los datos en el historial


                        val intent = Intent(this, MainActivity2::class.java)
                        intent.putExtra("destination_latitude", point.latitude())
                        intent.putExtra("destination_longitude", point.longitude())
                        startActivity(intent)

                        //actualizo los desgastes
                        lifecycleScope.launch {
                            val roundedDistance = distanciaKilometros?.toInt() ?: 0
                            updateAllComponentsKilometers(roundedDistance)
                        }
                    }
                }
            } ?: run {
                // Muestra un mensaje de error si selectedCoordinate es null
                showToast("Por favor, seleccione una dirección antes de navegar.")
            }
        }

        val closeCard = findViewById<ImageView>(R.id.close_card)
        closeCard.setOnClickListener {
            findViewById<CardView>(R.id.card_view).visibility = View.GONE
        }

        searchResultsView = findViewById(R.id.search_results_view)
        searchResultsView.initialize(
            SearchResultsView.Configuration(
                commonConfiguration = CommonSearchViewConfiguration(DistanceUnitType.METRIC)
            )
        )


        addressAutofillUiAdapter = AddressAutofillUiAdapter(
            view = searchResultsView,
            addressAutofill = addressAutofill
        )

        LocationEngineProvider.getBestLocationEngine(applicationContext).lastKnownLocation(this) { point ->
            point?.let {
                mapView.getMapboxMap().setCamera(
                    CameraOptions.Builder()
                        .center(point)
                        .zoom(9.0)
                        .build()
                )
                ignoreNextMapIdleEvent = true
            }
        }

        addressAutofillUiAdapter.addSearchListener(object : AddressAutofillUiAdapter.SearchListener {

            override fun onSuggestionSelected(suggestion: AddressAutofillSuggestion) {
                showAddressAutofillSuggestion(
                    suggestion,
                    fromReverseGeocoding = false,
                )
            }

            override fun onSuggestionsShown(suggestions: List<AddressAutofillSuggestion>) {
                // Nothing to do
            }

            override fun onError(e: Exception) {
                // Nothing to do
            }
        })

        queryEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (ignoreNextQueryTextUpdate) {
                    ignoreNextQueryTextUpdate = false

                }else {
                    buttonNavigateCard.isVisible = false

                }

                val query = Query.create(text.toString())
                if (query != null) {
                    lifecycleScope.launchWhenStarted {
                        addressAutofillUiAdapter.search(query)
                    }
                }
                searchResultsView.isVisible = query != null


            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Nothing to do
            }

            override fun afterTextChanged(s: Editable) {
                // Nothing to do
            }
        })

        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_LOCATION
            )
        }
    }



    private fun showComposeView(route: String) {
        val composeView = ComposeView(this)
        setContentView(composeView)

        showingComposeScreen = true

        composeView.setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = route) {
                composable(AppScreens.HistorialScreen.route) { HistorialScrenn(navController) }
                composable(AppScreens.RecambiosScreen.route) { RecambiosScrenn(navController) }
                composable(AppScreens.MenuNeumaticosScreen.route) { MenuNeumaticosScreen(navController) }
                composable(AppScreens.MenuFiltrosScreen.route) { MenuFiltrosScrenn(navController) }
                composable(AppScreens.MenuFrenosScreen.route) { MenuFrenosScrenn(navController) }
                composable(AppScreens.MenuLiquidosScreen.route) { MenuLiquidosScrenn(navController) }
                composable(AppScreens.MenuAceitesScreen.route) { MenuAceitesScrenn(navController) }
                composable(AppScreens.MenuOtrosComponentesScreen.route) { MenuOtrosComponentesScrenn(navController) }


            }
        }
    }


    private fun findAddress(point: Point) {

        lifecycleScope.launchWhenStarted {
            val response = addressAutofill.suggestions(point, AddressAutofillOptions())
            response.onValue { suggestions ->
                if (suggestions.isEmpty()) {
                    showToast(R.string.address_autofill_error_pin_correction)
                } else {
                    showAddressAutofillSuggestion(
                        suggestions.first(),
                        fromReverseGeocoding = true
                    )
                }
            }.onError { error ->
                Log.d("Test.", "Test. $error", error)
                showToast(R.string.address_autofill_error_pin_correction)
            }
        }
    }


    private fun insertHistorial() {
        val origen = direccionOrigen ?: ""
        val destino = direccionDestino ?: ""
        val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaActual ?: "") ?: Date()
        val distancia = distanciaKilometros ?: 0.0
        val distanciaRounded = Math.round(distancia).toInt()

        //val tiempo = 4.2//tiempoHoras

        val tiempo = distancia / AVERAGE_SPEED_KMH

        val fechaFormateada = SimpleDateFormat("EEE, d MMM yyyy", Locale("es", "ES")).format(fecha)

        Log.e("999999",tiempo.toString())
        Log.e("999999",origen)
        Log.e("999999",destino)
        Log.e("999999",fechaFormateada.toString())
        Log.e("999999",distanciaRounded.toString())

        historialViewModel.insertarHistorial(origen, destino, distanciaRounded, tiempo, fechaFormateada)
    }


    private fun showAddressAutofillSuggestion(suggestion: AddressAutofillSuggestion, fromReverseGeocoding: Boolean) {
        val address = suggestion.result().address

            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .center(suggestion.coordinate)
                    .zoom(16.0)
                    .build()
            )
            ignoreNextMapIdleEvent = true
            mapPin.isVisible = true

            // Guarda la dirección de destino
            direccionDestino = listOfNotNull(
                address.street,
                address.houseNumber,
                address.place,
            ).joinToString()
        buttonNavigateCard.isVisible = true

        selectedCoordinate = suggestion.coordinate // Guarda las coordenadas aquí
        ignoreNextQueryTextUpdate = true
        queryEditText.setText(
            listOfNotNull(
                address.street,
                address.houseNumber,
            ).joinToString()
        )
        queryEditText.clearFocus()

        searchResultsView.isVisible = false
        searchResultsView.hideKeyboard()
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private companion object {
        const val PERMISSIONS_REQUEST_LOCATION = 0
    }

    private fun updateComponentKilometers(id: String, newKilometers: Int) {
        componentViewModel.updateComponentKilometers(id, newKilometers)
        Log.e("ooooooooooooooooooo",id)
        Log.e("ooooooooooooooooooo",newKilometers.toString())
    }

    private suspend fun updateAllComponentsKilometers(newKilometers: Int) {
        val result = componentsRepository.getComponets()

        if (result is com.example.formapp.data.Result.Success) {
            val allComponents = result.data
            if (allComponents != null) {
                for (component in allComponents) {
                    val updatedKilometers = component.kilometers + newKilometers
                    updateComponentKilometers(component.id, updatedKilometers)
                }
            }
        } else {
            // Manejar el caso de error aquí, por ejemplo, mostrar un mensaje de error
        }
    }


    override fun onBackPressed() {
        if (showingComposeScreen) {
            showingComposeScreen = false
            setContentView(R.layout.activity_address_autofill)
            // No olvides volver a configurar todos los listeners y componentes de la vista en este punto
            addressAutofill = AddressAutofill.create(getString(R.string.mapbox_access_token))

            queryEditText = findViewById(R.id.query_text)

            mapPin = findViewById(R.id.map_pin)
            val redColor = ContextCompat.getColor(this, R.color.red)
            mapPin.setColorFilter(redColor, PorterDuff.Mode.SRC_IN)

            mapView = findViewById(R.id.map)
            mapboxMap = mapView.getMapboxMap()
            mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
            mapboxMap.addOnMapIdleListener {
                if (ignoreNextMapIdleEvent) {
                    ignoreNextMapIdleEvent = false
                    return@addOnMapIdleListener
                }

                val mapCenter = mapboxMap.cameraState.center
                findAddress(mapCenter)
            }

            buttonHistorial= findViewById(R.id.button2)
            buttonHistorial.setOnClickListener {
                showComposeView(AppScreens.HistorialScreen.route)
            }

            buttonRecambios = findViewById(R.id.button1)
            buttonRecambios.setOnClickListener {
                showComposeView(AppScreens.RecambiosScreen.route)
            }


            buttonNavigateCard = findViewById(R.id.card_view)
            buttonNavigate = findViewById(R.id.button_navigate)
            buttonNavigate.setOnClickListener {
                selectedCoordinate?.let { point ->
                    // Asigna la fecha actual
                    fechaActual = getCurrentDate()

                    // Calcula la distancia en kilómetros
                    val locationEngine = LocationEngineProvider.getBestLocationEngine(applicationContext)
                    locationEngine.lastKnownLocation(this) { origin ->
                        origin?.let {

                            val originPoint = Point.fromLngLat(origin.longitude(), origin.latitude())
                            Log.e("222222222222",originPoint.toString())

                            val distanceMeters = TurfMeasurement.distance(
                                Point.fromLngLat(origin.longitude(), origin.latitude()),
                                point,
                                TurfConstants.UNIT_KILOMETERS
                            )
                            distanciaKilometros = distanceMeters

                            //guardo direccion origen

                            lifecycleScope.launchWhenStarted {
                                val response = addressAutofill.suggestions(originPoint, AddressAutofillOptions())


                                ///inseto
                                val roundedDistance = distanciaKilometros?.toInt() ?: 0
                                updateAllComponentsKilometers(roundedDistance)

                                response.onValue { suggestions ->

                                    if (suggestions.isNotEmpty()) {
                                        val address = suggestions.first().result().address
                                        direccionOrigen = listOfNotNull(
                                            address.street,
                                            address.houseNumber,
                                            address.place,
                                        ).joinToString()
                                        Log.e("222222222222",direccionOrigen.toString())
                                        insertHistorial()
                                    }
                                }.onError { error ->
                                    Log.d("MainActivity4", "Error al obtener la dirección de origen: $error", error)
                                }
                            }

                            // Llama a la función para insertar los datos en el historial

                            val intent = Intent(this, MainActivity2::class.java)
                            intent.putExtra("destination_latitude", point.latitude())
                            intent.putExtra("destination_longitude", point.longitude())
                            startActivity(intent)
                        }
                    }
                } ?: run {
                    // Muestra un mensaje de error si selectedCoordinate es null
                    showToast("Por favor, seleccione una dirección antes de navegar.")
                }
            }

            val closeCard = findViewById<ImageView>(R.id.close_card)
            closeCard.setOnClickListener {
                findViewById<CardView>(R.id.card_view).visibility = View.GONE
            }


            searchResultsView = findViewById(R.id.search_results_view)

            searchResultsView.initialize(
                SearchResultsView.Configuration(
                    commonConfiguration = CommonSearchViewConfiguration(DistanceUnitType.METRIC)
                )
            )


            addressAutofillUiAdapter = AddressAutofillUiAdapter(
                view = searchResultsView,
                addressAutofill = addressAutofill
            )

            LocationEngineProvider.getBestLocationEngine(applicationContext).lastKnownLocation(this) { point ->
                point?.let {
                    mapView.getMapboxMap().setCamera(
                        CameraOptions.Builder()
                            .center(point)
                            .zoom(9.0)
                            .build()
                    )
                    ignoreNextMapIdleEvent = true
                }
            }

            addressAutofillUiAdapter.addSearchListener(object : AddressAutofillUiAdapter.SearchListener {

                override fun onSuggestionSelected(suggestion: AddressAutofillSuggestion) {
                    showAddressAutofillSuggestion(
                        suggestion,
                        fromReverseGeocoding = false,
                    )
                }

                override fun onSuggestionsShown(suggestions: List<AddressAutofillSuggestion>) {
                    // Nothing to do
                }

                override fun onError(e: Exception) {
                    // Nothing to do
                }
            })

            queryEditText.addTextChangedListener(object : TextWatcher {

                override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                    if (ignoreNextQueryTextUpdate) {
                        ignoreNextQueryTextUpdate = false

                    }else {
                        buttonNavigateCard.isVisible = false                }

                    val query = Query.create(text.toString())
                    if (query != null) {
                        lifecycleScope.launchWhenStarted {
                            addressAutofillUiAdapter.search(query)
                        }
                    }
                    searchResultsView.isVisible = query != null


                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // Nothing to do
                }

                override fun afterTextChanged(s: Editable) {
                    // Nothing to do
                }
            })

            if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    PERMISSIONS_REQUEST_LOCATION
                )
            }
        } else {
            super.onBackPressed()
        }
    }
}

package com.gonzalez.tupmundial2026

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configuración básica de Retrofit (ajusta la URL base según tu API)
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tu-api-base-url.com/")  // ← CAMBIA ESTO
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val api = retrofit.create(MundialApiService::class.java)
        val repository = MundialRepository(api)

        setContent {
            TUPMundial2026Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel { MundialViewModel(repository) }
                    )
                }
            }
        }
    }
}
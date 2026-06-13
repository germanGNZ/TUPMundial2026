@Composable
fun DetalleScreen(partidoId: Int, viewModel: MundialViewModel) {
    val detalle = viewModel.partidoDetalle
    val isLoading = viewModel.isLoadingDetalle

    LaunchedEffect(partidoId) {
        viewModel.LlamarDetalle(partidoId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (detalle != null) {
            Text("${detalle.equipo1} vs ${detalle.equipo2}",
                style = MaterialTheme.typography.headlineMedium)
            Text("Fecha: ${detalle.fecha}")
            Text("Grupo: ${detalle.grupo}")
            Text("Estadio: ${detalle.estadio}")
            Text("Precio: ${detalle.precio}")
        }
    }
}
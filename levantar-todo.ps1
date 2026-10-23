$base = "E:\MicroServicios"
$servicios = "validar","catalogo","cita","historia","receta","analisis","atencion"

foreach ($s in $servicios) {
    Write-Host "Iniciando $s..."
    Start-Process powershell -ArgumentList "-NoExit","-Command","cd '$base\$s'; mvn spring-boot:run"
    Start-Sleep -Seconds 8
}

Write-Host "Listo. Espera unos segundos y abre http://localhost:8080/login"
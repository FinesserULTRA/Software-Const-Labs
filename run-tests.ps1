# PowerShell script to run all JUnit tests for PS2

$binPath = "bin"
$junit = "c:/Users/musta/Downloads/junit-4.13.2.jar"
$hamcrest = "c:/Users/musta/Downloads/hamcrest-core-1.3.jar"

# Compile all Java files
Write-Host "Compiling Java files..." -ForegroundColor Yellow
javac -d $binPath -cp "$junit;$hamcrest" src\graph\*.java src\poet\*.java test\graph\*.java test\poet\*.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!" -ForegroundColor Green
    
    # Run tests
    Write-Host "`nRunning tests..." -ForegroundColor Yellow
    Write-Host "----------------------------------------"-ForegroundColor Green
    Write-Host "ConcreteVerticesGraph Tests"-ForegroundColor Green
    Write-Host "ConcreteEdgesGraph Tests"-ForegroundColor Green
    Write-Host "----------------------------------------"-ForegroundColor Green
    java -ea -cp "$binPath;$junit;$hamcrest" org.junit.runner.JUnitCore graph.ConcreteEdgesGraphTest graph.ConcreteVerticesGraphTest poet.GraphPoetTest
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "`nAll tests passed!" -ForegroundColor Green
    } else {
        Write-Host "`nSome tests failed!" -ForegroundColor Red
    }
} else {
    Write-Host "Compilation failed!" -ForegroundColor Red
}

@echo off
cd /d "%~dp0windows-agent"
dotnet publish MobiPC.WindowsAgent.csproj -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true
pause

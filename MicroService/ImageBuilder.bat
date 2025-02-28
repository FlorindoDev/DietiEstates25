@echo off

REM Crea la rete Docker
docker network create dieti_network

REM Esegue il build delle immagini
docker build -t dependacy:1.0.0 .\Dependacy
docker build -t access_micro_service:1.0.0 .\AccessService
docker build -t admin_management_service:1.0.0 .\AdminManagementService
docker build -t ads_estate_service:1.0.0 .\AdsEstateService
docker build -t agency_service:1.0.0 .\AgencyService
docker build -t agent_management_service:1.0.0 .\AgentManagementService
docker build -t appointment_service:1.0.0 .\AppointmentService
docker build -t management_account_service:1.0.0 .\ManagementAccountService

REM Per escludere il build di SearchService, lasciamo il comando commentato:
REM docker build -t search_service:1.0.0 .\SearchService

echo Script completato!
pause
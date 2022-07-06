@echo off
title AUTOMATIZACION

:MENU
cls
echo 1. BPT ACH Adicionar Cuenta
echo 2. BPT ACH Cuenta Recurrente
echo 3. BPT MOVIL Adicionar Cuenta
echo 4. BPT MOVIL Cuenta Recurrente
echo 5. BPT Terceros Adicionar Cuenta
echo 6. BPT Transferencias Propias
echo 7. BPT Ver Cuentas
echo 8. BPT Login Masivo
echo 9. GTCAPP ACH Adicionar Cuenta
echo 10. GTCAPP ACH Cuenta Recurrente
echo 11. GTCAPP Transferencia a Terceros
echo 12. GTCAPP Transferencias Propias
echo 13. GTCAPP Ver Cuentas
echo 14. GTCAPP Transferencias Moviles Enviar
echo 15. GTCAPP Transferencias Moviles Cobrar
echo 16. GTCAPP Transferencias Moviles Solicitar
echo 17. GTCAPP Pago Tarjeta de Credito
echo 18. GTCAPP Pago Recargas Claro
echo 19. GTCAPP Pago ClaroTV y Turbonet
echo 20. GTCAPP Pago Recargas Tigo
echo 21. GTCAPP Pago TELGUA
echo 22. GTCAPP Pago Colegio de Abogados
echo 23. GTCAPP Pago Colegio de Ingenieros Químicos
echo 24. Banca Empresarial Estado de cuenta
echo 25. Banca Empresarial Transferencias Propias Manual
echo 26. Banca Empresarial Transferencias Propias Archivo
echo 27. Banca Empresarial Transferencias a Terceros Manual
echo 28. Banca Empresarial Transferencias a Terceros Archivo
echo 29. Caja Web Transaccion 1992
echo 30. Caja Web Transaccion 1999
echo 31. Caja Web Transacción 2010
echo 32. Caja Web Transacción 3208
echo 33. Caja Web Transacción 3209
echo 34. Caja Web Transacción 3310
echo 35. Caja Web Transacción 3500
echo 36. Caja Web Transacción 8200
echo 37. Caja Web Transacción 8250
echo 38. Caja Web Transacción 8251
echo 39. Portal Financiero LCCON
echo 40. Portal Financiero AHC03
echo 41. Portal Financiero AC107
echo 42. Portal Financiero MOC03
echo 43. Portal Financiero ACCSA
echo 44. Portal Financiero AHCSA
echo 45. Portal Financiero AHT07
echo 46. Portal Financiero MOT02
echo 47. Portal Financiero CM956
echo 48. Portal Financiero AAMBO
echo 49. Portal Financiero AATIB
echo 50. Portal Financiero AATIC
echo 51. Portal Financiero BNABA
echo 52. Portal Financiero ACCC1
echo 53. Portal Financiero CUA03
echo 54. Portal Financiero SG001
echo 55. Portal Financiero ACCE1
echo 56. Portal Financiero CUA04
echo 57. Portal Financiero AAREB
echo 58. Portal Financiero AAPRO
echo 59. Portal Financiero AATMM
echo 60. Portal Financiero CRTCY


echo Digite Opcion..
set/p "cho=>>"


if %cho%==1 goto BPTI_ACH_AdicionarCuenta
if %cho%==2 goto BPTI_ACH_Recurrentes
if %cho%==3 goto BPTI_MovilAdicionarCuenta
if %cho%==4 goto BPTI_MovilCuentaRecurrente
if %cho%==5 goto BPTI_Terceros_AdicionarCuenta
if %cho%==6 goto BPTI_TransferenciasPropias
if %cho%==7 goto BPTI_VerCuentas
if %cho%==8 goto BPTI_ANTIGUA_EstadoDeCuenta
if %cho%==9 goto GTCApp_ACH_AdicionarCuenta
if %cho%==10 goto GTCApp_ACHRecurrente
if %cho%==11 goto GTCApp_Transferencias_a_Terceros
if %cho%==12 goto GTCApp_Transferencias_Propias
if %cho%==13 goto GTCApp_VerCuentas
if %cho%==14 goto GTCApp_Moviles_Enviar
if %cho%==15 goto GTCApp_Moviles_Cobrar
if %cho%==16 goto GTCApp_Moviles_Solicitar
if %cho%==17 goto GTCApp_PagoTC
if %cho%==18 goto GTCApp_RecargasClaro
if %cho%==19 goto GTCAPP_PagoClaroTV_Turbonet
if %cho%==20 goto GTCApp_Recargas_TIGO
if %cho%==21 goto GTCApp_PagoTelgua
if %cho%==22 goto GTCAPP_PagoColegioAbogados
if %cho%==23 goto GTCAPP_PagoColegioIngenierosQuimicos
if %cho%==24 goto BE_EstadoDeCuenta
if %cho%==25 goto BE_TPropias_Manual
if %cho%==26 goto BE_TPropias_Archivo
if %cho%==27 goto BE_TransferenciasATerceros_Manual
if %cho%==28 goto BE_TransferenciasATerceros_Archivo
if %cho%==29 goto CajaWeb_1992
if %cho%==30 goto CajaWeb_1999
if %cho%==31 goto CajaWeb_2010
if %cho%==32 goto CajaWeb_3208
if %cho%==33 goto CajaWeb_3209
if %cho%==34 goto CajaWeb_3310
if %cho%==35 goto CajaWeb_3500
if %cho%==36 goto CajaWeb_8200
if %cho%==37 goto CajaWeb_8250
if %cho%==38 goto CajaWeb_8251
if %cho%==39 goto PortalFinanciero_LCCON
if %cho%==40 goto PortalFinanciero_AHC03
if %cho%==41 goto PortalFinanciero_AC107
if %cho%==42 goto PortalFinanciero_MOC03
if %cho%==43 goto PortalFinanciero_ACCSA
if %cho%==44 goto PortalFinanciero_AHCSA
if %cho%==45 goto PortalFinanciero_AHT07
if %cho%==46 goto PortalFinanciero_MOT02
if %cho%==47 goto PortalFinanciero_CM956
if %cho%==48 goto PortalFinanciero_AAMBO
if %cho%==49 goto PortalFinanciero_AATIB
if %cho%==50 goto PortalFinanciero_AATIC
if %cho%==51 goto PortalFinanciero_BNABA
if %cho%==52 goto PortalFinanciero_ACCC1
if %cho%==53 goto PortalFinanciero_CUA03
if %cho%==54 goto PortalFinanciero_SG001
if %cho%==55 goto PortalFinanciero_ACCE1
if %cho%==56 goto PortalFinancieroCUA04
if %cho%==57 goto PortalFinanciero_AAREB
if %cho%==58 goto PortalFinancieroAAPRO
if %cho%==59 goto PortalFinancieroAATMM
if %cho%==60 goto PortalFinanciero_CRTCY

echo ???
goto MENU
pause


:BPTI_ACH_AdicionarCuenta
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_ACH_AdicionarCuenta.xml
pause


:BPTI_ACH_Recurrentes
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_ACH_Recurrentes.xml
pause


:BPTI_MovilAdicionarCuenta
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_MovilAdicionarCuenta.xml
pause


:BPTI_MovilCuentaRecurrente
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_MovilCuentaRecurrente.xml
pause


:BPTI_Terceros_AdicionarCuenta
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_Terceros_AdicionarCuenta.xml
pause


:BPTI_TransferenciasPropias
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_TransferenciasPropias.xml
pause

:BPTI_VerCuentas
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_VerCuentas.xml
pause

:BPTI_ANTIGUA_EstadoDeCuenta
mvn clean test -Dsurefire.suiteXmlFiles=BPTI_ANTIGUA_EstadoDeCuenta.xml
pause


:GTCApp_ACH_AdicionarCuenta
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_ACH_AdicionarCuenta.xml
pause


:GTCApp_ACHRecurrente
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_ACHRecurrente.xml
pause


:GTCApp_Transferencias_a_Terceros
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_Transferencias_a_Terceros.xml
pause

:GTCApp_Transferencias_Propias
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_Transferencias_Propias.xml
pause


:GTCApp_Transferencias_Propias
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_VerCuentas.xml
pause


:GTCApp_Moviles_Enviar
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_Moviles_Enviar.xml
pause


:GTCApp_Moviles_Cobrar
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_Moviles_Cobrar.xml
pause


:GTCApp_Moviles_Solicitar
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_Moviles_Solicitar.xml
pause


:GTCApp_PagoTC
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_PagoTC.xml
pause


:GTCApp_RecargasClaro
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_RecargasClaro.xml
pause


:GTCAPP_PagoClaroTV_Turbonet
mvn clean test -Dsurefire.suiteXmlFiles=GTCAPP_PagoClaroTV_Turbonet.xml
pause

:GTCApp_Recargas_TIGO
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_Recargas_TIGO.xml
pause

:GTCApp_PagoTelgua
mvn clean test -Dsurefire.suiteXmlFiles=GTCApp_PagoTelgua.xml
pause

:GTCAPP_PagoColegioAbogados
mvn clean test -Dsurefire.suiteXmlFiles=GTCAPP_PagoColegioAbogados.xml
pause

:GTCAPP_PagoColegioIngenierosQuimicos
mvn clean test -Dsurefire.suiteXmlFiles=GTCAPP_PagoColegioIngenierosQuimicos.xml
pause

:BE_EstadoDeCuenta
mvn clean test -Dsurefire.suiteXmlFiles=BE_EstadoDeCuenta.xml
pause

:BE_TPropias_Manual
mvn clean test -Dsurefire.suiteXmlFiles=BE_TPropias_Manual.xml
pause

:BE_TPropias_Archivo
mvn clean test -Dsurefire.suiteXmlFiles=BE_TPropias_Archivo.xml
pause

:BE_TransferenciasATerceros_Manual
mvn clean test -Dsurefire.suiteXmlFiles=BE_TransferenciasATerceros_Manual.xml
pause

:BE_TransferenciasATerceros_Archivo
mvn clean test -Dsurefire.suiteXmlFiles=BE_TransferenciasATerceros_Archivo.xml
pause

:CajaWeb_1992
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_1992.xml
pause

:CajaWeb_1999
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_1999.xml
pause

:CajaWeb_2010
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_2010.xml
pause

:CajaWeb_3208
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_3208.xml
pause

:CajaWeb_3209
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_3209.xml
pause

:CajaWeb_3310
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_3310.xml
pause

:CajaWeb_3500
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_3500.xml
pause

:CajaWeb_8200
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_8200.xml
pause

:CajaWeb_8250
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_8250.xml
pause

:CajaWeb_8251
mvn clean test -Dsurefire.suiteXmlFiles=CajaWeb_8251.xml
pause

:PortalFinanciero_LCCON
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_LCCON.xml
pause


:PortalFinanciero_AHC03
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AHC03.xml
pause


:PortalFinanciero_AC107
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AC107.xml
pause


:PortalFinanciero_MOC03
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_MOC03.xml
pause

:PortalFinanciero_ACCSA
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_ACCSA.xml
pause

:PortalFinanciero_AHCSA
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AHCSA.xml
pause

:PortalFinanciero_AHT07
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AHT07.xml
pause

:PortalFinanciero_MOT02
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_MOT02.xml
pause

:PortalFinanciero_CM956
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_CM956.xml
pause

:PortalFinanciero_AAMBO
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AAMBO.xml
pause

:PortalFinanciero_AATIB
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AATIB.xml
pause

:PortalFinanciero_AATIC
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AATIC.xml
pause

:PortalFinanciero_BNABA
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_BNABA.xml
pause

:PortalFinanciero_ACCC1
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_ACCC1.xml
pause

:PortalFinanciero_CUA03
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_CUA03.xml
pause

:PortalFinanciero_SG001
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_SG001.xml
pause

:PortalFinanciero_ACCE1
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_ACCE1.xml
pause

:PortalFinancieroCUA04
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinancieroCUA04.xml
pause

:PortalFinanciero_AAREB
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_AAREB.xml
pause

:PortalFinancieroAAPRO
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinancieroAAPRO.xml
pause

:PortalFinancieroAATMM
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinancieroAATMM.xml
pause

:PortalFinanciero_CRTCY
mvn clean test -Dsurefire.suiteXmlFiles=PortalFinanciero_CRTCY.xml
pause
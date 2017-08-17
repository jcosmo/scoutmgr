## Scoutmgr
PreBootstrap
  -> new ScoutmgrApp().init()

Bootstrap
  Inject App & Injector
  app->setInjector( injector )
  app->start( )
   
ScoutmgrApp
  * Has no injection capabilies
  constructor()
    -> ClientModule.setApp(this)

  init()
    -> initGwtRpcServices

  start()
    prestart();  
      //nothing
    setupAppCache()
    setupUncaughtExceptionHandler();
    prepareServices();
      FrontEndContect->connect()
    prepareUI();
      Ensure resource bundle
      Remove loading message
    postStart();
      start login
      
  

## Places app

Entrypoint@onModuleLoad
  -> new iris.planner.client.PlannerApp().init();

PlannerApp
  * Has no injection capabilities
  constructor()
    -> EntryPointModule.setApp(this)
  AbstractPlannerApp#init()
    -> initGwtRpcServices
    -> Start keycloak
      -> calls back to onReady
  AbstractPlannerApp#onReady
    -> initiate login
    or
    -> start
  AbstractPlannerApp#start
    preStart();
      construct injector and store in PlannerApp
    setupAppCache();
    setupUncaughtExceptionHandler();
    prepareServices();
      FrontEndContect->connect()
    prepareUI();
      Ensure resource bundle
      Inject mainPanel into DOM
      ActivityManager.setDisplay(mainPanel)
    postStart();
      // Goes to place represented on URL or default place
      ensureInjector().getPlaceHistoryHandler().handleCurrentHistory();

EntryPointModule
  * Adds the app to the injector so others can get it
  
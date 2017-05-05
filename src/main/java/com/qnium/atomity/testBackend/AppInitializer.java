/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnium.atomity.testBackend;

import com.qnium.common.backend.assets.dataobjects.CollectionResponseMessage;
import com.qnium.common.backend.assets.dataobjects.CountResponseMessage;
import com.qnium.common.backend.assets.dataobjects.ReadRequestParameters;
import com.qnium.common.backend.assets.dataobjects.RequestMessage;
import com.qnium.common.backend.assets.handlers.generic.CreateHandler;
import com.qnium.common.backend.assets.handlers.generic.DeleteHandler;
import com.qnium.common.backend.assets.handlers.generic.ReadHandler;
import com.qnium.common.backend.assets.handlers.generic.UpdateHandler;
import com.qnium.common.backend.core.CommonValidationManager;
import com.qnium.common.backend.core.ConfigManager;
import com.qnium.common.backend.core.EntityManager;
import com.qnium.common.backend.core.EntityManagerStorage;
import com.qnium.common.backend.core.HandlerWrapper;
import com.qnium.common.backend.core.HandlersManager;
import com.qnium.common.backend.core.Logger;
import com.qnium.common.validation.ValidationManager;
import com.qnium.common.validation.handlers.ValidatorsHandler;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.j256.ormlite.field.DataPersisterManager;
import com.qnium.common.backend.assets.dataobjects.ResponseMessage;
import com.qnium.atomity.testBackend.dataobjects.Department;
import com.qnium.atomity.testBackend.dataobjects.Employee;
import com.qnium.atomity.testBackend.handlers.FakeAction;
import com.qnium.atomity.testBackend.utils.InstantTypePersister;
import java.util.List;

/**
 *
 * @author Drozhin
 */
@WebListener
public class AppInitializer implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce) throws ExceptionInInitializerError
    {
        Logger.log.info("Test dev application initialization...");
        
        try
        {
            registerOrmLiteTypes();
            ServletContext config = sce.getServletContext();
            ConfigManager cm = ConfigManager.getInstance();
            cm.setDatabaseDriverName(config.getInitParameter("databaseDriverName"));
            cm.setDatabaseURL(config.getInitParameter("databaseURL"));

            ConfigurationManager.getInstance().initConfig(config);
            
            initEntityHandlers();
            initHandlersManager(config);
            initEntityManagerStorage();            
        }
        catch(Exception ex)
        {
            Logger.log.error("Application initialization failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }    
    
    static void initEntityHandlers() throws SQLException
    {     
//        EntityManager<ShopUser> shopUserManager = EntityManager.getInstance(ShopUser.class);
//        shopUserManager.addHandler(new CheckAllowedUsersNumber(), EntityHandlerType.BEFORE_CREATE_HANDLER);
    }
    
    static void initEntityManagerStorage() throws Exception
    {
        EntityManagerStorage entityManagerStorage = EntityManagerStorage.getInstance();

        entityManagerStorage.addEntityManager("employee", EntityManager.getInstance(Employee.class));
        entityManagerStorage.addEntityManager("department", EntityManager.getInstance(Department.class));
    }
    
    static void initHandlersManager(ServletContext config) throws Exception
    {
        String action;
        ReadHandler readHandler;
        CreateHandler createHandler;
        UpdateHandler updateHandler;
        DeleteHandler deleteHandler;
        HandlersManager handlersManager = HandlersManager.getInstance();
    
        action = "employee.create";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<Employee>>() {},
                new TypeReference<CountResponseMessage>() {},
                new CreateHandler()));
        action = "employee.read";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<ReadRequestParameters>>() {},
                new TypeReference<CollectionResponseMessage>() {},
                new ReadHandler()));
        action = "employee.update";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<List<Employee>>>() {},
                new TypeReference<CountResponseMessage>() {},
                new UpdateHandler()));
        action = "employee.delete";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<List<Employee>>>() {},
                new TypeReference<CountResponseMessage>() {},
                new DeleteHandler()));
        action = "employee.fakeAction";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage>() {},
                new TypeReference<ResponseMessage>() {},
                new FakeAction()));
        
        action = "department.create";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<Department>>() {},
                new TypeReference<CountResponseMessage>() {},
                new CreateHandler()));
        action = "department.read";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<ReadRequestParameters>>() {},
                new TypeReference<CollectionResponseMessage>() {},
                new ReadHandler()));
        action = "department.update";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<List<Department>>>() {},
                new TypeReference<CountResponseMessage>() {},
                new UpdateHandler()));
        action = "department.delete";
        handlersManager.addHandler(action, new HandlerWrapper(
                new TypeReference<RequestMessage<List<Department>>>() {},
                new TypeReference<CountResponseMessage>() {},
                new DeleteHandler()));
        
//        readHandler.addBeforeHandler(new ForeignValueShopFilter());
//        readHandler.addAfterHandler(new FillEntityShopField<>() );
        
//        action = "shops.shopRead";
//        readHandler = new ReadHandler();
//        readHandler.addBeforeHandler(new ReadFilteringByShop(new ShopIdProvider(), Shop.ID));
//        handlersManager.addHandler(action, new HandlerWrapper(
//                new TypeReference<RequestMessage<ReadRequestParameters>>() {},
//                new TypeReference<CollectionResponseMessage>() {},
//                readHandler));
//        action = "shops.shopUpdate";
//        updateHandler = new UpdateHandler();
//        updateHandler.addBeforeHandler(new CheckOwnerUpdatingShop());
//        handlersManager.addHandler(action, new HandlerWrapper(
//                new TypeReference<RequestMessage<UpdateRequestParameters<Shop>>>() {},
//                new TypeReference<CountResponseMessage>() {},
//                updateHandler));
//        action = "shops.readRelatedEntities";
//        AuthorizationManager.getInstance().addPermission(AccountType.ADMIN, action);
//        AuthorizationManager.getInstance().addPermission(AccountType.SHOP, action);
//        handlersManager.addHandler(action, new HandlerWrapper(
//                new TypeReference<RequestMessage<FieldFilter>>() {},
//                new TypeReference<ObjectResponseMessage<List<Long>>>() {},
//                new ReadRelatedEntitiesHandler()));
        
        CommonValidationManager.getInstance().setValidationProvider( object -> { ValidationManager.getInstance().validateObject(object); }); //Validator registration
        ValidatorsHandler valhandler;
        
        action = "employee.validators";
        valhandler = new ValidatorsHandler(Employee.class);
        handlersManager.addHandler(action, valhandler.getWrapper());
        action = "department.validators";
        valhandler = new ValidatorsHandler(Employee.class);
        handlersManager.addHandler(action, valhandler.getWrapper());
    }
    
    private void registerOrmLiteTypes() {
        DataPersisterManager.registerDataPersisters(new InstantTypePersister());
    }
}

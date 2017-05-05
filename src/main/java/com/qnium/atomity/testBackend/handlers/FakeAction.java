/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qnium.atomity.testBackend.handlers;

import com.qnium.common.backend.assets.dataobjects.RequestMessage;
import com.qnium.common.backend.assets.dataobjects.ResponseMessage;
import com.qnium.common.backend.assets.interfaces.IHandler;
import com.qnium.common.backend.exceptions.CommonException;
import java.io.IOException;

/**
 *
 * @author Drozhin
 */
public class FakeAction implements IHandler<RequestMessage, ResponseMessage>
{
    @Override
    public ResponseMessage process(RequestMessage request) throws IOException, CommonException
    {
        return new ResponseMessage();        
    }    
}

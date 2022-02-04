package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;

abstract public class Document implements BytesChangeable, Dictionariable {
    protected Hint hint;
    protected Info info;
    protected Address owner;
    
    protected Document(String docType, Info info, String owner) {
        this.hint = new Hint(docType);
        this.info = info;
        this.owner = new Address(owner);
    }
}

package com.example.safeblock;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class UserData_SmartContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061111e806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063971afd341161005b578063971afd34146100d9578063cab63d89146100ec578063ed5e81dc146100ff578063fba1bc4c1461011057600080fd5b8063365b98b21461008257806339911a02146100b157806359fee299146100c6575b600080fd5b610095610090366004610c2c565b610125565b6040516100a89796959493929190610c9d565b60405180910390f35b6100c46100bf366004610dbb565b6103a1565b005b6100c46100d4366004610e43565b6104f1565b6100c46100e7366004610dbb565b6105c5565b6100c46100fa366004610e43565b610704565b6000546040519081526020016100a8565b6101186108ba565b6040516100a89190610ea7565b6000818154811061013557600080fd5b60009182526020909120600790910201805460018201546002830180549294506001600160a01b03909116929161016b90610f9a565b80601f016020809104026020016040519081016040528092919081815260200182805461019790610f9a565b80156101e45780601f106101b9576101008083540402835291602001916101e4565b820191906000526020600020905b8154815290600101906020018083116101c757829003601f168201915b5050505050908060030180546101f990610f9a565b80601f016020809104026020016040519081016040528092919081815260200182805461022590610f9a565b80156102725780601f1061024757610100808354040283529160200191610272565b820191906000526020600020905b81548152906001019060200180831161025557829003601f168201915b50505050509080600401805461028790610f9a565b80601f01602080910402602001604051908101604052809291908181526020018280546102b390610f9a565b80156103005780601f106102d557610100808354040283529160200191610300565b820191906000526020600020905b8154815290600101906020018083116102e357829003601f168201915b50505050509080600501805461031590610f9a565b80601f016020809104026020016040519081016040528092919081815260200182805461034190610f9a565b801561038e5780601f106103635761010080835404028352916020019161038e565b820191906000526020600020905b81548152906001019060200180831161037157829003601f168201915b5050506006909301549192505060ff1687565b60005b6000548110156104eb57836040516020016103bf9190610fd4565b60405160208183030381529060405280519060200120600082815481106103e8576103e8610ff0565b90600052602060002090600702016002016040516020016104099190611006565b6040516020818303038152906040528051906020012014801561049a5750826040516020016104389190610fd4565b604051602081830303815290604052805190602001206000828154811061046157610461610ff0565b90600052602060002090600702016003016040516020016104829190611006565b60405160208183030381529060405280519060200120145b156104d95781600082815481106104b3576104b3610ff0565b906000526020600020906007020160030190805190602001906104d7929190610b93565b505b806104e3816110b7565b9150506103a4565b50505050565b60005b6000548110156105c0578260405160200161050f9190610fd4565b604051602081830303815290604052805190602001206000828154811061053857610538610ff0565b90600052602060002090600702016002016040516020016105599190611006565b60405160208183030381529060405280519060200120036105ae57816000828154811061058857610588610ff0565b906000526020600020906007020160030190805190602001906105ac929190610b93565b505b806105b8816110b7565b9150506104f4565b505050565b60006040518060e0016040528060008054905060016105e491906110d0565b815233602080830191909152604080830188905280518082018252600d81526c04e6f742059657420536574757609c1b8184015260608401526080830187905260a08301869052600060c09093018390528454600180820187559584529282902084516007909402019283558382015194830180546001600160a01b0319166001600160a01b03909616959095179094559282015180519293919261068f9260028501920190610b93565b50606082015180516106ab916003840191602090910190610b93565b50608082015180516106c7916004840191602090910190610b93565b5060a082015180516106e3916005840191602090910190610b93565b5060c091909101516006909101805460ff1916911515919091179055505050565b60005b6000548110156105c057336001600160a01b03166000828154811061072e5761072e610ff0565b60009182526020909120600160079092020101546001600160a01b03161480156107c65750826040516020016107649190610fd4565b604051602081830303815290604052805190602001206000828154811061078d5761078d610ff0565b90600052602060002090600702016002016040516020016107ae9190611006565b60405160208183030381529060405280519060200120145b80156108405750816040516020016107de9190610fd4565b604051602081830303815290604052805190602001206000828154811061080757610807610ff0565b90600052602060002090600702016003016040516020016108289190611006565b60405160208183030381529060405280519060200120145b156108a8576000818154811061085857610858610ff0565b60009182526020822060066007909202010154815460ff9091161591908390811061088557610885610ff0565b60009182526020909120600790910201600601805460ff19169115159190911790555b806108b2816110b7565b915050610707565b60606000805480602002602001604051908101604052809291908181526020016000905b82821015610b8a5760008481526020908190206040805160e081018252600786029092018054835260018101546001600160a01b0316938301939093526002830180549293929184019161093190610f9a565b80601f016020809104026020016040519081016040528092919081815260200182805461095d90610f9a565b80156109aa5780601f1061097f576101008083540402835291602001916109aa565b820191906000526020600020905b81548152906001019060200180831161098d57829003601f168201915b505050505081526020016003820180546109c390610f9a565b80601f01602080910402602001604051908101604052809291908181526020018280546109ef90610f9a565b8015610a3c5780601f10610a1157610100808354040283529160200191610a3c565b820191906000526020600020905b815481529060010190602001808311610a1f57829003601f168201915b50505050508152602001600482018054610a5590610f9a565b80601f0160208091040260200160405190810160405280929190818152602001828054610a8190610f9a565b8015610ace5780601f10610aa357610100808354040283529160200191610ace565b820191906000526020600020905b815481529060010190602001808311610ab157829003601f168201915b50505050508152602001600582018054610ae790610f9a565b80601f0160208091040260200160405190810160405280929190818152602001828054610b1390610f9a565b8015610b605780601f10610b3557610100808354040283529160200191610b60565b820191906000526020600020905b815481529060010190602001808311610b4357829003601f168201915b50505091835250506006919091015460ff16151560209182015290825260019290920191016108de565b50505050905090565b828054610b9f90610f9a565b90600052602060002090601f016020900481019282610bc15760008555610c07565b82601f10610bda57805160ff1916838001178555610c07565b82800160010185558215610c07579182015b82811115610c07578251825591602001919060010190610bec565b50610c13929150610c17565b5090565b5b80821115610c135760008155600101610c18565b600060208284031215610c3e57600080fd5b5035919050565b60005b83811015610c60578181015183820152602001610c48565b838111156104eb5750506000910152565b60008151808452610c89816020860160208601610c45565b601f01601f19169290920160200192915050565b8781526001600160a01b038716602082015260e060408201819052600090610cc790830188610c71565b8281036060840152610cd98188610c71565b90508281036080840152610ced8187610c71565b905082810360a0840152610d018186610c71565b91505082151560c083015298975050505050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f830112610d3f57600080fd5b813567ffffffffffffffff80821115610d5a57610d5a610d18565b604051601f8301601f19908116603f01168101908282118183101715610d8257610d82610d18565b81604052838152866020858801011115610d9b57600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600060608486031215610dd057600080fd5b833567ffffffffffffffff80821115610de857600080fd5b610df487838801610d2e565b94506020860135915080821115610e0a57600080fd5b610e1687838801610d2e565b93506040860135915080821115610e2c57600080fd5b50610e3986828701610d2e565b9150509250925092565b60008060408385031215610e5657600080fd5b823567ffffffffffffffff80821115610e6e57600080fd5b610e7a86838701610d2e565b93506020850135915080821115610e9057600080fd5b50610e9d85828601610d2e565b9150509250929050565b60006020808301818452808551808352604092508286019150828160051b87010184880160005b83811015610f8c57888303603f19018552815180518452878101516001600160a01b0316888501528681015160e088860181905290610f0f82870182610c71565b91505060608083015186830382880152610f298382610c71565b9250505060808083015186830382880152610f448382610c71565b9250505060a08083015186830382880152610f5f8382610c71565b9250505060c0808301519250610f788187018415159052565b509588019593505090860190600101610ece565b509098975050505050505050565b600181811c90821680610fae57607f821691505b602082108103610fce57634e487b7160e01b600052602260045260246000fd5b50919050565b60008251610fe6818460208701610c45565b9190910192915050565b634e487b7160e01b600052603260045260246000fd5b600080835481600182811c91508083168061102257607f831692505b6020808410820361104157634e487b7160e01b86526022600452602486fd5b818015611055576001811461106657611093565b60ff19861689528489019650611093565b60008a81526020902060005b8681101561108b5781548b820152908501908301611072565b505084890196505b509498975050505050505050565b634e487b7160e01b600052601160045260246000fd5b6000600182016110c9576110c96110a1565b5060010190565b600082198211156110e3576110e36110a1565b50019056fea2646970667358221220057c98efdb5d7848491cf4fb195536d431bb48a5eedf64c839323616e9f9882264736f6c634300080d0033";

    public static final String FUNC_CREATE_USER_DATA = "create_user_data";

    public static final String FUNC_GET_USER = "get_user";

    public static final String FUNC_GET_USER_LIST_LENGTH = "get_user_list_length";

    public static final String FUNC_UPDATE_NEW_USER_EMAIL = "update_new_user_email";

    public static final String FUNC_UPDATE_OLD_USER_EMAIL = "update_old_user_email";

    public static final String FUNC_UPDATE_STATUS_INFECTED = "update_status_infected";

    public static final String FUNC_USERS = "users";

    @Deprecated
    protected UserData_SmartContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UserData_SmartContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UserData_SmartContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UserData_SmartContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> create_user_data(String _name, String _placeVisited, String _timeVisited) {
        final Function function = new Function(
                FUNC_CREATE_USER_DATA, 
                Arrays.<Type>asList(new Utf8String(_name),
                new Utf8String(_placeVisited),
                new Utf8String(_timeVisited)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> get_user() {
        final Function function = new Function(FUNC_GET_USER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<user>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> get_user_list_length() {
        final Function function = new Function(FUNC_GET_USER_LIST_LENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> update_new_user_email(String _name, String _email) {
        final Function function = new Function(
                FUNC_UPDATE_NEW_USER_EMAIL, 
                Arrays.<Type>asList(new Utf8String(_name),
                new Utf8String(_email)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> update_old_user_email(String _name, String _email, String _newEmail) {
        final Function function = new Function(
                FUNC_UPDATE_OLD_USER_EMAIL, 
                Arrays.<Type>asList(new Utf8String(_name),
                new Utf8String(_email),
                new Utf8String(_newEmail)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> update_status_infected(String _name, String _email) {
        final Function function = new Function(
                FUNC_UPDATE_STATUS_INFECTED, 
                Arrays.<Type>asList(new Utf8String(_name),
                new Utf8String(_email)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, String, String, String, String, Boolean>> users(BigInteger param0) {
        final Function function = new Function(FUNC_USERS, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, String, String, String, String, Boolean>>(function,
                new Callable<Tuple7<BigInteger, String, String, String, String, String, Boolean>>() {
                    @Override
                    public Tuple7<BigInteger, String, String, String, String, String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, String, String, String, String, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    @Deprecated
    public static UserData_SmartContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserData_SmartContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UserData_SmartContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserData_SmartContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UserData_SmartContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UserData_SmartContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UserData_SmartContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UserData_SmartContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UserData_SmartContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserData_SmartContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserData_SmartContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserData_SmartContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<UserData_SmartContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserData_SmartContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserData_SmartContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserData_SmartContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}

package com.example.safeblock;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
public class UserData_sol_UserData extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610d22806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c8063365b98b21461006757806339911a021461009657806359fee299146100ab578063971afd34146100be578063cab63d89146100d1578063ed5e81dc146100e4575b600080fd5b61007a610075366004610923565b6100f5565b60405161008d9796959493929190610994565b60405180910390f35b6100a96100a4366004610ab2565b610371565b005b6100a96100b9366004610b3a565b6104c1565b6100a96100cc366004610ab2565b610595565b6100a96100df366004610b3a565b6106d4565b60005460405190815260200161008d565b6000818154811061010557600080fd5b60009182526020909120600790910201805460018201546002830180549294506001600160a01b03909116929161013b90610b9e565b80601f016020809104026020016040519081016040528092919081815260200182805461016790610b9e565b80156101b45780601f10610189576101008083540402835291602001916101b4565b820191906000526020600020905b81548152906001019060200180831161019757829003601f168201915b5050505050908060030180546101c990610b9e565b80601f01602080910402602001604051908101604052809291908181526020018280546101f590610b9e565b80156102425780601f1061021757610100808354040283529160200191610242565b820191906000526020600020905b81548152906001019060200180831161022557829003601f168201915b50505050509080600401805461025790610b9e565b80601f016020809104026020016040519081016040528092919081815260200182805461028390610b9e565b80156102d05780601f106102a5576101008083540402835291602001916102d0565b820191906000526020600020905b8154815290600101906020018083116102b357829003601f168201915b5050505050908060050180546102e590610b9e565b80601f016020809104026020016040519081016040528092919081815260200182805461031190610b9e565b801561035e5780601f106103335761010080835404028352916020019161035e565b820191906000526020600020905b81548152906001019060200180831161034157829003601f168201915b5050506006909301549192505060ff1687565b60005b6000548110156104bb578360405160200161038f9190610bd8565b60405160208183030381529060405280519060200120600082815481106103b8576103b8610bf4565b90600052602060002090600702016002016040516020016103d99190610c0a565b6040516020818303038152906040528051906020012014801561046a5750826040516020016104089190610bd8565b604051602081830303815290604052805190602001206000828154811061043157610431610bf4565b90600052602060002090600702016003016040516020016104529190610c0a565b60405160208183030381529060405280519060200120145b156104a957816000828154811061048357610483610bf4565b906000526020600020906007020160030190805190602001906104a792919061088a565b505b806104b381610cbb565b915050610374565b50505050565b60005b60005481101561059057826040516020016104df9190610bd8565b604051602081830303815290604052805190602001206000828154811061050857610508610bf4565b90600052602060002090600702016002016040516020016105299190610c0a565b604051602081830303815290604052805190602001200361057e57816000828154811061055857610558610bf4565b9060005260206000209060070201600301908051906020019061057c92919061088a565b505b8061058881610cbb565b9150506104c4565b505050565b60006040518060e0016040528060008054905060016105b49190610cd4565b815233602080830191909152604080830188905280518082018252600d81526c04e6f742059657420536574757609c1b8184015260608401526080830187905260a08301869052600060c09093018390528454600180820187559584529282902084516007909402019283558382015194830180546001600160a01b0319166001600160a01b03909616959095179094559282015180519293919261065f926002850192019061088a565b506060820151805161067b91600384019160209091019061088a565b506080820151805161069791600484019160209091019061088a565b5060a082015180516106b391600584019160209091019061088a565b5060c091909101516006909101805460ff1916911515919091179055505050565b60005b60005481101561059057336001600160a01b0316600082815481106106fe576106fe610bf4565b60009182526020909120600160079092020101546001600160a01b03161480156107965750826040516020016107349190610bd8565b604051602081830303815290604052805190602001206000828154811061075d5761075d610bf4565b906000526020600020906007020160020160405160200161077e9190610c0a565b60405160208183030381529060405280519060200120145b80156108105750816040516020016107ae9190610bd8565b60405160208183030381529060405280519060200120600082815481106107d7576107d7610bf4565b90600052602060002090600702016003016040516020016107f89190610c0a565b60405160208183030381529060405280519060200120145b15610878576000818154811061082857610828610bf4565b60009182526020822060066007909202010154815460ff9091161591908390811061085557610855610bf4565b60009182526020909120600790910201600601805460ff19169115159190911790555b8061088281610cbb565b9150506106d7565b82805461089690610b9e565b90600052602060002090601f0160209004810192826108b857600085556108fe565b82601f106108d157805160ff19168380011785556108fe565b828001600101855582156108fe579182015b828111156108fe5782518255916020019190600101906108e3565b5061090a92915061090e565b5090565b5b8082111561090a576000815560010161090f565b60006020828403121561093557600080fd5b5035919050565b60005b8381101561095757818101518382015260200161093f565b838111156104bb5750506000910152565b6000815180845261098081602086016020860161093c565b601f01601f19169290920160200192915050565b8781526001600160a01b038716602082015260e0604082018190526000906109be90830188610968565b82810360608401526109d08188610968565b905082810360808401526109e48187610968565b905082810360a08401526109f88186610968565b91505082151560c083015298975050505050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f830112610a3657600080fd5b813567ffffffffffffffff80821115610a5157610a51610a0f565b604051601f8301601f19908116603f01168101908282118183101715610a7957610a79610a0f565b81604052838152866020858801011115610a9257600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600060608486031215610ac757600080fd5b833567ffffffffffffffff80821115610adf57600080fd5b610aeb87838801610a25565b94506020860135915080821115610b0157600080fd5b610b0d87838801610a25565b93506040860135915080821115610b2357600080fd5b50610b3086828701610a25565b9150509250925092565b60008060408385031215610b4d57600080fd5b823567ffffffffffffffff80821115610b6557600080fd5b610b7186838701610a25565b93506020850135915080821115610b8757600080fd5b50610b9485828601610a25565b9150509250929050565b600181811c90821680610bb257607f821691505b602082108103610bd257634e487b7160e01b600052602260045260246000fd5b50919050565b60008251610bea81846020870161093c565b9190910192915050565b634e487b7160e01b600052603260045260246000fd5b600080835481600182811c915080831680610c2657607f831692505b60208084108203610c4557634e487b7160e01b86526022600452602486fd5b818015610c595760018114610c6a57610c97565b60ff19861689528489019650610c97565b60008a81526020902060005b86811015610c8f5781548b820152908501908301610c76565b505084890196505b509498975050505050505050565b634e487b7160e01b600052601160045260246000fd5b600060018201610ccd57610ccd610ca5565b5060010190565b60008219821115610ce757610ce7610ca5565b50019056fea2646970667358221220eafc93306ba1c43103259dfdfdf7ff429c3016731d7262286131f474f531387664736f6c634300080d0033";

    public static final String FUNC_CREATE_USER_DATA = "create_user_data";

    public static final String FUNC_GET_USER_LIST_LENGTH = "get_user_list_length";

    public static final String FUNC_UPDATE_NEW_USER_EMAIL = "update_new_user_email";

    public static final String FUNC_UPDATE_OLD_USER_EMAIL = "update_old_user_email";

    public static final String FUNC_UPDATE_STATUS_INFECTED = "update_status_infected";

    public static final String FUNC_USERS = "users";

    @Deprecated
    protected UserData_sol_UserData(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UserData_sol_UserData(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UserData_sol_UserData(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UserData_sol_UserData(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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
    public static UserData_sol_UserData load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserData_sol_UserData(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UserData_sol_UserData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserData_sol_UserData(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UserData_sol_UserData load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UserData_sol_UserData(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UserData_sol_UserData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UserData_sol_UserData(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UserData_sol_UserData> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserData_sol_UserData.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserData_sol_UserData> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserData_sol_UserData.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<UserData_sol_UserData> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(UserData_sol_UserData.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<UserData_sol_UserData> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(UserData_sol_UserData.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}

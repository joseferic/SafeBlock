package com.example.safeblock;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
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
public class SafeBlock_sol_SafeBlock extends Contract {
    public static final String BINARY = "6080604052600060015534801561001557600080fd5b5061079c806100256000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80636b5c03d31461005c578063a80ed5b414610071578063e9dda84d14610088578063ec6c4cce14610091578063f0ba8440146100a4575b600080fd5b61006f61006a3660046104e2565b6100c5565b005b6000545b6040519081526020015b60405180910390f35b61007560015481565b61006f61009f36600461054e565b610194565b6100b76100b2366004610590565b610248565b60405161007f9291906105f6565b60005b60015481101561018d5782826040516020016100e5929190610624565b604051602081830303815290604052805190602001206000828154811061010e5761010e610634565b906000526020600020906002020160010160405160200161012f9190610684565b604051602081830303815290604052805190602001200361017b5784846000838154811061015f5761015f610634565b60009182526020909120610179936002909202019161038c565b505b8061018581610735565b9150506100c8565b5050505050565b604080516060810182526000818301818152825282516020601f86018190048102820181019094528481529092808301919086908690819084018382808284376000920182905250939094525050835460018101855593815260209081902083518051949560020290910193610211935084929190910190610410565b50602082810151805161022a9260018501920190610410565b505050600180600082825461023f919061074e565b90915550505050565b6000818154811061025857600080fd5b906000526020600020906002020160009150905080600001805461027b9061064a565b80601f01602080910402602001604051908101604052809291908181526020018280546102a79061064a565b80156102f45780601f106102c9576101008083540402835291602001916102f4565b820191906000526020600020905b8154815290600101906020018083116102d757829003601f168201915b5050505050908060010180546103099061064a565b80601f01602080910402602001604051908101604052809291908181526020018280546103359061064a565b80156103825780601f1061035757610100808354040283529160200191610382565b820191906000526020600020905b81548152906001019060200180831161036557829003601f168201915b5050505050905082565b8280546103989061064a565b90600052602060002090601f0160209004810192826103ba5760008555610400565b82601f106103d35782800160ff19823516178555610400565b82800160010185558215610400579182015b828111156104005782358255916020019190600101906103e5565b5061040c929150610484565b5090565b82805461041c9061064a565b90600052602060002090601f01602090048101928261043e5760008555610400565b82601f1061045757805160ff1916838001178555610400565b82800160010185558215610400579182015b82811115610400578251825591602001919060010190610469565b5b8082111561040c5760008155600101610485565b60008083601f8401126104ab57600080fd5b50813567ffffffffffffffff8111156104c357600080fd5b6020830191508360208285010111156104db57600080fd5b9250929050565b600080600080604085870312156104f857600080fd5b843567ffffffffffffffff8082111561051057600080fd5b61051c88838901610499565b9096509450602087013591508082111561053557600080fd5b5061054287828801610499565b95989497509550505050565b6000806020838503121561056157600080fd5b823567ffffffffffffffff81111561057857600080fd5b61058485828601610499565b90969095509350505050565b6000602082840312156105a257600080fd5b5035919050565b6000815180845260005b818110156105cf576020818501810151868301820152016105b3565b818111156105e1576000602083870101525b50601f01601f19169290920160200192915050565b60408152600061060960408301856105a9565b828103602084015261061b81856105a9565b95945050505050565b8183823760009101908152919050565b634e487b7160e01b600052603260045260246000fd5b600181811c9082168061065e57607f821691505b60208210810361067e57634e487b7160e01b600052602260045260246000fd5b50919050565b600080835481600182811c9150808316806106a057607f831692505b602080841082036106bf57634e487b7160e01b86526022600452602486fd5b8180156106d357600181146106e457610711565b60ff19861689528489019650610711565b60008a81526020902060005b868110156107095781548b8201529085019083016106f0565b505084890196505b509498975050505050505050565b634e487b7160e01b600052601160045260246000fd5b6000600182016107475761074761071f565b5060010190565b600082198211156107615761076161071f565b50019056fea2646970667358221220219e7df928c4098c7705a7ee933e8e466d26e999bf6f402b17c535f58b2d456364736f6c634300080d0033";

    public static final String FUNC_CREATEUSERDATA = "createUserData";

    public static final String FUNC_DATA = "data";

    public static final String FUNC_GETDATALISTLENGTH = "getDataListLength";

    public static final String FUNC_UPDATETRANSACTIONHASH = "updateTransactionHash";

    public static final String FUNC_USERSARRAYLENGTH = "usersArrayLength";

    @Deprecated
    protected SafeBlock_sol_SafeBlock(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SafeBlock_sol_SafeBlock(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SafeBlock_sol_SafeBlock(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SafeBlock_sol_SafeBlock(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> createUserData(String dataUser) {
        final Function function = new Function(
                FUNC_CREATEUSERDATA, 
                Arrays.<Type>asList(new Utf8String(dataUser)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<String, String>> data(BigInteger param0) {
        final Function function = new Function(FUNC_DATA, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getDataListLength() {
        final Function function = new Function(FUNC_GETDATALISTLENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateTransactionHash(String transactionHash, String dataUser) {
        final Function function = new Function(
                FUNC_UPDATETRANSACTIONHASH, 
                Arrays.<Type>asList(new Utf8String(transactionHash),
                new Utf8String(dataUser)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> usersArrayLength() {
        final Function function = new Function(FUNC_USERSARRAYLENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static SafeBlock_sol_SafeBlock load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SafeBlock_sol_SafeBlock(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SafeBlock_sol_SafeBlock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SafeBlock_sol_SafeBlock(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SafeBlock_sol_SafeBlock load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SafeBlock_sol_SafeBlock(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SafeBlock_sol_SafeBlock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SafeBlock_sol_SafeBlock(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SafeBlock_sol_SafeBlock> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SafeBlock_sol_SafeBlock.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SafeBlock_sol_SafeBlock> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SafeBlock_sol_SafeBlock.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SafeBlock_sol_SafeBlock> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SafeBlock_sol_SafeBlock.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SafeBlock_sol_SafeBlock> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SafeBlock_sol_SafeBlock.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}

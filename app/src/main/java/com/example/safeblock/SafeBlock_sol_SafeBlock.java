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
import org.web3j.tuples.generated.Tuple3;
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
    public static final String BINARY = "6080604052600060015534801561001557600080fd5b50610858806100256000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80636b5c03d31461005c578063903fa32f14610071578063a80ed5b414610084578063e9dda84d1461009b578063f0ba8440146100a4575b600080fd5b61006f61006a3660046105cb565b6100c6565b005b61006f61007f3660046105cb565b610195565b6000545b6040519081526020015b60405180910390f35b61008860015481565b6100b76100b2366004610637565b6102a3565b6040516100929392919061069d565b60005b60015481101561018e5782826040516020016100e69291906106e0565b604051602081830303815290604052805190602001206000828154811061010f5761010f6106f0565b90600052602060002090600302016001016040516020016101309190610740565b604051602081830303815290604052805190602001200361017c57848460008381548110610160576101606106f0565b6000918252602090912061017a9360039092020191610475565b505b80610186816107f1565b9150506100c9565b5050505050565b60408051608081018252600060608201818152825282516020601f8801819004810282018101909452868152909280830191908890889081908401838280828437600092019190915250505090825250604080516020601f870181900481028201810190925285815291810191908690869081908401838280828437600092018290525093909452505083546001810185559381526020908190208351805194956003029091019361024e9350849291909101906104f9565b50602082810151805161026792600185019201906104f9565b50604082015180516102839160028401916020909101906104f9565b5050506001806000828254610298919061080a565b909155505050505050565b600081815481106102b357600080fd5b90600052602060002090600302016000915090508060000180546102d690610706565b80601f016020809104026020016040519081016040528092919081815260200182805461030290610706565b801561034f5780601f106103245761010080835404028352916020019161034f565b820191906000526020600020905b81548152906001019060200180831161033257829003601f168201915b50505050509080600101805461036490610706565b80601f016020809104026020016040519081016040528092919081815260200182805461039090610706565b80156103dd5780601f106103b2576101008083540402835291602001916103dd565b820191906000526020600020905b8154815290600101906020018083116103c057829003601f168201915b5050505050908060020180546103f290610706565b80601f016020809104026020016040519081016040528092919081815260200182805461041e90610706565b801561046b5780601f106104405761010080835404028352916020019161046b565b820191906000526020600020905b81548152906001019060200180831161044e57829003601f168201915b5050505050905083565b82805461048190610706565b90600052602060002090601f0160209004810192826104a357600085556104e9565b82601f106104bc5782800160ff198235161785556104e9565b828001600101855582156104e9579182015b828111156104e95782358255916020019190600101906104ce565b506104f592915061056d565b5090565b82805461050590610706565b90600052602060002090601f01602090048101928261052757600085556104e9565b82601f1061054057805160ff19168380011785556104e9565b828001600101855582156104e9579182015b828111156104e9578251825591602001919060010190610552565b5b808211156104f5576000815560010161056e565b60008083601f84011261059457600080fd5b50813567ffffffffffffffff8111156105ac57600080fd5b6020830191508360208285010111156105c457600080fd5b9250929050565b600080600080604085870312156105e157600080fd5b843567ffffffffffffffff808211156105f957600080fd5b61060588838901610582565b9096509450602087013591508082111561061e57600080fd5b5061062b87828801610582565b95989497509550505050565b60006020828403121561064957600080fd5b5035919050565b6000815180845260005b818110156106765760208185018101518683018201520161065a565b81811115610688576000602083870101525b50601f01601f19169290920160200192915050565b6060815260006106b06060830186610650565b82810360208401526106c28186610650565b905082810360408401526106d68185610650565b9695505050505050565b8183823760009101908152919050565b634e487b7160e01b600052603260045260246000fd5b600181811c9082168061071a57607f821691505b60208210810361073a57634e487b7160e01b600052602260045260246000fd5b50919050565b600080835481600182811c91508083168061075c57607f831692505b6020808410820361077b57634e487b7160e01b86526022600452602486fd5b81801561078f57600181146107a0576107cd565b60ff198616895284890196506107cd565b60008a81526020902060005b868110156107c55781548b8201529085019083016107ac565b505084890196505b509498975050505050505050565b634e487b7160e01b600052601160045260246000fd5b600060018201610803576108036107db565b5060010190565b6000821982111561081d5761081d6107db565b50019056fea264697066735822122018132d11116d8ee66b3edb91f65f83d82874b29fb34259e153e377562969e91064736f6c634300080d0033";

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

    public RemoteFunctionCall<TransactionReceipt> createUserData(String dataUser, String key) {
        final Function function = new Function(
                FUNC_CREATEUSERDATA, 
                Arrays.<Type>asList(new Utf8String(dataUser),
                new Utf8String(key)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<String, String, String>> data(BigInteger param0) {
        final Function function = new Function(FUNC_DATA, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, String>>(function,
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
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

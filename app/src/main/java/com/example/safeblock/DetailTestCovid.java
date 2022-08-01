package com.example.safeblock;

public class DetailTestCovid {
    String userName;
    String testId;
    String tipeTesCovid;
    String tanggalTes;
    String waktuTes;
    String DoctorName;
    String DoctorAddress;
    String transactionHash;
    String statusTransaksi;
    String priceEther;
    String priceRupiah;

    public DetailTestCovid(
            String userName,
            String testId,
            String tipeTesCovid,
            String tanggalTes,
            String waktuTes,
            String DoctorName,
            String DoctorAddress,
            String transactionHash,
            String statusTransaksi,
            String priceEther,
            String priceRupiah
    ){
        this.userName = userName;
        this.testId = testId;
        this.tipeTesCovid = tipeTesCovid;
        this.tanggalTes = tanggalTes;
        this.waktuTes = waktuTes;
        this.DoctorName = DoctorName;
        this.DoctorAddress = DoctorAddress;
        this.transactionHash = transactionHash;
        this.statusTransaksi = statusTransaksi;
        this.priceEther = priceEther;
        this.priceRupiah = priceRupiah;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "Nama User = " + userName +
                "\n\nNama Dokter = " + DoctorName +
                "\n\nAddress Dokter = " + DoctorAddress +
                "\n\nTipe Tes Covid-19 = " + tipeTesCovid
                + "\n\nTanggal = " + tanggalTes +
                "\n\nWaktu = " + waktuTes
                + "\n\nTest Id = " + testId
                + "\n\nHarga dalam Rupiah = " + priceRupiah
                + "\n\nHarga dalam Ether = " + priceEther
                + "\n\nStatus Transaksi = " + statusTransaksi
                + "\n\nTransaction Hash = " + transactionHash;
    }
}

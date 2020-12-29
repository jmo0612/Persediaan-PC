package persediaanpc.util;

public class QueryHelperPersediaan {
    public static final String qListPengadaan="SELECT\n" +
"det.id_mutasi, \n" +
"det.id_tipe, \n" +
"det.ket_tipe, \n" +
"det.tgl_mutasi, \n" +
"det.no_ba_mutasi, \n" +
"det.id_jenis_mutasi, \n" +
"det.nm_pihak2, \n" +
"det.nip_pihak2, \n" +
"det.almt_pihak2, \n" +
"det.jab_pihak2, \n" +
"det.instansi_pihak2, \n" +
"det.nm_pengguna_brg, \n" +
"det.nip_pengguna_brg, \n" +
"det.jab_pengguna_brg, \n" +
"det.nm_tu_brg, \n" +
"det.nip_tu_brg, \n" +
"det.jab_tu_brg, \n" +
"det.nm_pengurus_brg, \n" +
"det.nip_pengurus_brg, \n" +
"det.jab_pengurus_brg, \n" +
"det.no_dok_dasar, \n" +
"det.tgl_dok_dasar, \n" +
"det.no_dok_dasar2, \n" +
"det.tgl_dok_dasar2, \n" +
"det.id_bidang, \n" +
"det.nm_bidang, \n" +
"det.approved, \n" +
"det.printed, \n" +
"SUM(det.real_harga_penerimaan*det.real_qty) AS total\n" +
"\n" +
"\n" +
"FROM\n" +
"(SELECT\n" +
"p_tb_mutasi_det_real.*,\n" +
"p_tb_item.nm_item,\n" +
"p_tb_item.satuan,\n" +
"p_tb_item.id_kat,\n" +
"p_tb_item.ket_kat,\n" +
"p_tb_item.id_tipe,\n" +
"p_tb_item.ket_tipe,\n" +
"p_tb_mutasi.tgl_mutasi,\n" +
"p_tb_mutasi.no_ba_mutasi,\n" +
"p_tb_mutasi.id_jenis_mutasi,\n" +
"p_tb_mutasi.nm_pihak2,\n" +
"p_tb_mutasi.nip_pihak2,\n" +
"p_tb_mutasi.almt_pihak2,\n" +
"p_tb_mutasi.jab_pihak2,\n" +
"p_tb_mutasi.instansi_pihak2,\n" +
"p_tb_mutasi.nm_pengguna_brg,\n" +
"p_tb_mutasi.nip_pengguna_brg,\n" +
"p_tb_mutasi.jab_pengguna_brg,\n" +
"p_tb_mutasi.nm_tu_brg,\n" +
"p_tb_mutasi.nip_tu_brg,\n" +
"p_tb_mutasi.jab_tu_brg,\n" +
"p_tb_mutasi.nm_pengurus_brg,\n" +
"p_tb_mutasi.nip_pengurus_brg,\n" +
"p_tb_mutasi.jab_pengurus_brg,\n" +
"p_tb_mutasi.no_dok_dasar,\n" +
"p_tb_mutasi.tgl_dok_dasar,\n" +
"p_tb_mutasi.no_dok_dasar2,\n" +
"p_tb_mutasi.tgl_dok_dasar2,\n" +
"p_tb_mutasi.id_bidang,\n" +
"p_tb_mutasi.approved,\n" +
"p_tb_mutasi.printed,\n" +
"p_tb_mutasi.ket_jenis_mutasi,\n" +
"p_tb_mutasi.debit,\n" +
"p_tb_mutasi.struktur_ket,\n" +
"p_tb_mutasi.nm_bidang,\n" +
"REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(p_tb_mutasi.struktur_ket,'[URAIAN]',p_tb_item.ket_tipe),'[JAB]',p_tb_mutasi.jab_pihak2),'[BIDANG]',p_tb_mutasi.nm_bidang),'[NAMA]',p_tb_mutasi.nm_pihak2),'[INSTANSI]',p_tb_mutasi.instansi_pihak2) AS ket_detail\n" +
"\n" +
"FROM p_tb_mutasi_det_real\n" +
"JOIN \n" +
"	(SELECT \n" +
"     p_tb_item.*,\n" +
"     p_ref_kat.ket_kat,\n" +
"     p_ref_kat.id_tipe,\n" +
"     p_ref_kat.ket_tipe \n" +
"     FROM p_tb_item \n" +
"    JOIN\n" +
"    	(SELECT \n" +
"         p_ref_kat.*,\n" +
"         p_ref_tipe.ket_tipe \n" +
"         FROM p_ref_kat JOIN p_ref_tipe\n" +
"        ON p_ref_kat.id_tipe=p_ref_tipe.id_tipe) AS p_ref_kat\n" +
"    ON p_tb_item.id_kat=p_ref_kat.id_kat)AS p_tb_item\n" +
"ON p_tb_mutasi_det_real.id_item=p_tb_item.id_item\n" +
"JOIN \n" +
"	(SELECT \n" +
"     p_tb_mutasi.*, \n" +
"     p_ref_jenis_mutasi.ket_jenis_mutasi,\n" +
"     p_ref_jenis_mutasi.debit,\n" +
"     p_ref_jenis_mutasi.struktur_ket,\n" +
"     p_tb_bidang.nm_bidang\n" +
"     FROM p_tb_mutasi JOIN p_ref_jenis_mutasi\n" +
"     ON p_tb_mutasi.id_jenis_mutasi=p_ref_jenis_mutasi.id_jenis_mutasi\n" +
"     JOIN p_tb_bidang ON p_tb_mutasi.id_bidang=p_tb_bidang.id_bidang\n" +
"    ) AS p_tb_mutasi \n" +
"ON p_tb_mutasi_det_real.id_mutasi=p_tb_mutasi.id_mutasi\n" +
"WHERE debit='1') AS det\n" +
"GROUP BY id_mutasi\n" +
"ORDER BY tgl_mutasi asc";
    
    public static String qDetailPengadaan(String idMutasi){
        String tmp="SELECT det.*\n" +
"FROM\n" +
"(SELECT \n" +
"p_tb_mutasi_det_real.id_det_mutasi_real,\n" +
"p_tb_mutasi_det_real.id_mutasi,\n" +
"p_tb_mutasi_det_real.id_item,\n" +
"p_tb_item.nm_item,\n" +
"p_tb_mutasi_det_real.real_qty,\n" +
"p_tb_item.satuan,\n" +
"p_tb_mutasi_det_real.real_harga_penerimaan,\n" +
"(p_tb_mutasi_det_real.real_qty*p_tb_mutasi_det_real.real_harga_penerimaan) subtotal\n" +
"FROM p_tb_mutasi_det_real JOIN p_tb_item\n" +
"ON p_tb_mutasi_det_real.id_item=p_tb_item.id_item) AS det\n" +
"WHERE det.id_mutasi='[id_mutasi]'";
        
        return tmp.replace("[id_mutasi]", idMutasi);
    }
    
    public static String qDetRealUnApproved="SELECT p_tb_mutasi_det_real.id_det_mutasi_real, p_tb_mutasi_det_real.id_mutasi, p_tb_mutasi_det_real.id_item, p_tb_mutasi_det_real.real_qty, p_tb_mutasi_det_real.real_harga_penerimaan, p_tb_mutasi.tgl_mutasi FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_real AS p_tb_mutasi_det_real WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_real.id_mutasi AND p_tb_mutasi.approved = '0' ORDER BY p_tb_mutasi.tgl_mutasi ASC";
    
    public static String qDetRealUnApprovedGrouped="SELECT\n" +
"det.id_mutasi\n" +
"FROM\n" +
"(SELECT p_tb_mutasi_det_real.id_det_mutasi_real, p_tb_mutasi_det_real.id_mutasi, p_tb_mutasi_det_real.id_item, p_tb_mutasi_det_real.real_qty, p_tb_mutasi_det_real.real_harga_penerimaan, p_tb_mutasi.tgl_mutasi FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_real AS p_tb_mutasi_det_real WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_real.id_mutasi AND p_tb_mutasi.approved = '0' ORDER BY p_tb_mutasi.tgl_mutasi ASC) AS det\n" +
"GROUP BY id_mutasi";
    
    public static String qDelDetRealFromDate(String date){
        String tmp="DELETE p_tb_mutasi_det_buku \n" +
"FROM p_tb_mutasi_det_buku INNER JOIN p_tb_mutasi\n" +
"ON p_tb_mutasi_det_buku.id_mutasi=p_tb_mutasi.id_mutasi\n" +
"WHERE tgl_mutasi>='[date]'";
        return tmp.replace("[date]", date);
    }
    
    public static String qResetApprovalFromDate(String date){
        String tmp="UPDATE p_tb_mutasi SET approved='0' WHERE tgl_mutasi>='[date]'";
        return tmp.replace("[date]", date);
    }
    
    public static String qDetRealUnApprovedDebit="SELECT p_tb_mutasi_det_real.id_det_mutasi_real, p_tb_mutasi_det_real.id_mutasi, p_tb_mutasi_det_real.id_item, p_tb_mutasi_det_real.real_qty, p_tb_mutasi_det_real.real_harga_penerimaan, p_tb_mutasi.tgl_mutasi FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_real AS p_tb_mutasi_det_real, bmd.p_ref_jenis_mutasi AS p_ref_jenis_mutasi WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_real.id_mutasi AND p_ref_jenis_mutasi.id_jenis_mutasi = p_tb_mutasi.id_jenis_mutasi AND p_tb_mutasi.approved = '0' AND p_ref_jenis_mutasi.debit = '1' ORDER BY p_tb_mutasi.tgl_mutasi ASC";
    
    public static String qDetRealUnApprovedKredit="SELECT p_tb_mutasi_det_real.id_det_mutasi_real, p_tb_mutasi_det_real.id_mutasi, p_tb_mutasi_det_real.id_item, p_tb_mutasi_det_real.real_qty, p_tb_mutasi_det_real.real_harga_penerimaan, p_tb_mutasi.tgl_mutasi FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_real AS p_tb_mutasi_det_real, bmd.p_ref_jenis_mutasi AS p_ref_jenis_mutasi WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_real.id_mutasi AND p_ref_jenis_mutasi.id_jenis_mutasi = p_tb_mutasi.id_jenis_mutasi AND p_tb_mutasi.approved = '0' AND p_ref_jenis_mutasi.debit = '0' ORDER BY p_tb_mutasi.tgl_mutasi ASC";
    
    public static String qItemStockNotZero(String beforeDate, String idItem){
        String tmp="SELECT\n" +
"p_tb_subitem.*,\n" +
"look.stok\n" +
"FROM p_tb_subitem\n" +
"JOIN\n" +
"(SELECT\n" +
"stokitem.id_subitem,\n" +
"stokitem.stok_akhir AS stok\n" +
"FROM\n" +
"(SELECT \n" +
"p_tb_mutasi_det_buku.*,\n" +
"p_tb_mutasi.tgl_mutasi\n" +
"FROM p_tb_mutasi_det_buku JOIN p_tb_mutasi\n" +
"ON p_tb_mutasi_det_buku.id_mutasi=p_tb_mutasi.id_mutasi\n" +
"WHERE tgl_mutasi<'[beforeDate]' AND stok_akhir>'0'\n" +
"ORDER BY tgl_mutasi DESC) AS stokitem\n" +
"GROUP BY id_subitem) AS look\n" +
"ON p_tb_subitem.id_subitem=look.id_subitem\n" +
"WHERE id_item='[idItem]'\n" +
"ORDER BY id_item ASC, wkt_masuk_item ASC";
        return tmp.replace("[beforeDate]", beforeDate).replace("[idItem]", idItem);
    }
            
    
}

package persediaanpc.util;

import com.thowo.jmjavaframework.JMDate;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static final String qListPermintaan="SELECT\n" +
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
"WHERE debit='0') AS det\n" +
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
    
    public static String qDetailPermintaan(String idMutasi){
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
    
    //public static String qDetRealUnApproved="SELECT p_tb_mutasi_det_real.id_det_mutasi_real, p_tb_mutasi_det_real.id_mutasi, p_tb_mutasi_det_real.id_item, p_tb_mutasi_det_real.real_qty, p_tb_mutasi_det_real.real_harga_penerimaan, p_tb_mutasi.tgl_mutasi FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_real AS p_tb_mutasi_det_real WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_real.id_mutasi AND p_tb_mutasi.approved = '0' ORDER BY p_tb_mutasi.tgl_mutasi ASC";
    public static String qDetRealUnApproved="SELECT \n" +
"p_tb_mutasi_det_real.id_det_mutasi_real, \n" +
"p_tb_mutasi_det_real.id_mutasi, \n" +
"p_tb_mutasi_det_real.id_item, \n" +
"p_tb_mutasi_det_real.real_qty, \n" +
"p_tb_mutasi_det_real.real_harga_penerimaan,\n" +
"p_q_mutasi.tgl_mutasi,\n" +
"p_q_mutasi.debit,\n" +
"p_q_item.nm_item,\n" +
"p_tb_mutasi_det_real.excluded\n" +            
"FROM \n" +
"p_tb_mutasi_det_real\n" +
"join p_q_item on p_tb_mutasi_det_real.id_item=p_q_item.id_item\n" +
"join p_q_mutasi on p_tb_mutasi_det_real.id_mutasi=p_q_mutasi.id_mutasi\n" +
"where p_q_mutasi.approved='0' and p_tb_mutasi_det_real.excluded='0'\n" +
"order by tgl_mutasi asc, id_mutasi asc";
    
    public static String qDetRealSorted="SELECT \n" +
"p_tb_mutasi_det_real.id_det_mutasi_real, \n" +
"p_tb_mutasi_det_real.id_mutasi, \n" +
"p_tb_mutasi_det_real.id_item, \n" +
"p_tb_mutasi_det_real.real_qty, \n" +
"p_tb_mutasi_det_real.real_harga_penerimaan,\n" +
"p_q_mutasi.tgl_mutasi,\n" +
"p_q_mutasi.debit,\n" +
"p_q_item.nm_item\n" +
"FROM \n" +
"p_tb_mutasi_det_real\n" +
"join p_q_item on p_tb_mutasi_det_real.id_item=p_q_item.id_item\n" +
"join p_q_mutasi on p_tb_mutasi_det_real.id_mutasi=p_q_mutasi.id_mutasi\n" +
"order by tgl_mutasi asc, id_mutasi asc";
    
    public static String qDetRealUnApprovedGrouped="SELECT\n" +
"det.id_mutasi\n" +
"FROM\n" +
"(SELECT p_tb_mutasi_det_real.id_det_mutasi_real, p_tb_mutasi_det_real.id_mutasi, p_tb_mutasi_det_real.id_item, p_tb_mutasi_det_real.real_qty, p_tb_mutasi_det_real.real_harga_penerimaan, p_tb_mutasi.tgl_mutasi FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_real AS p_tb_mutasi_det_real WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_real.id_mutasi AND p_tb_mutasi.approved = '0' ORDER BY p_tb_mutasi.tgl_mutasi ASC) AS det\n" +
"GROUP BY id_mutasi";
    
    public static String qDelDetBukuFromDate(String date){
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
            
    public static String qListItemForBidangFromDate(String date, String idBidang){
        String tmp="select \n" +
"p_q_item.id_item ,\n" +
"p_q_item.ket_kat ,\n" +
"p_q_item.nm_item ,\n" +
"if(qStock.stok_akhir is null,0,qStock.stok_akhir) as total_stock,\n" +
"if(qQuota.realQty is null,0,qQuota.realQty) as bidang_stock,\n" +
"p_q_item.satuan ,\n" +
"p_q_item.id_kat \n" +
"from \n" +
"p_q_item\n" +
"left join\n" +
"	(\n" +
"		select *\n" +
"		from\n" +
"		(select \n" +
"		id_mutasi ,\n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		sum(stok_akhir)stok_akhir \n" +
"		from \n" +
"		p_q_mutasi_det_buku\n" +
"		where \n" +
"		tgl_mutasi <='[date]'\n" +
"		group by \n" +
"		id_item ,\n" +
"		id_mutasi \n" +
"		order by\n" +
"		tgl_mutasi desc, id_mutasi desc)qDet\n" +
"		group by\n" +
"		id_item \n" +
"	)qStock on p_q_item.id_item = qStock.id_item\n" +
"left join \n" +
"	(\n" +
"		select \n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		sum(if(debit='0',qty*-1,qty)) as realQty\n" +
"		from \n" +
"		p_q_mutasi_det_buku\n" +
"		where \n" +
"		tgl_mutasi <='[date]' and\n" +
"		id_bidang ='[idBidang]'\n" +
"		group by\n" +
"		id_item\n" +
"	)qQuota on p_q_item.id_item = qQuota.id_item";
        
        return tmp.replace("[date]", date).replace("[idBidang]", idBidang);
    }
    
    /*
    public static String qListItemForBidangFromDate(String date, String idBidang){
        String tmp="SELECT \n" +
"p_tb_item.id_item,\n" +
"p_ref_kat.ket_kat,\n" +
"p_tb_item.nm_item,\n" +
"IF(qStockAll.stok IS NOT NULL,qStockAll.stok,0)AS total_stock,\n" +
"IF(qStockBidang.stok IS NOT NULL,qStockBidang.stok,0)AS bidang_stock,\n" +
"p_tb_item.satuan,\n" +
"p_tb_item.id_kat\n" +
"FROM p_tb_item\n" +
"LEFT JOIN \n" +
"\n" +
"\n" +
"(\n" +
"SELECT \n" +
"p_tb_item.*,\n" +
"(@idKey:=p_tb_item.id_item)prm,\n" +
"(\n" +
"	SELECT \n" +
"    mutBuku.stok_akhir\n" +
"    FROM \n" +
"    	(\n" +
"            SELECT\n" +
"            p_tb_mutasi_det_buku.*,\n" +
"            p_tb_subitem.id_item\n" +
"            FROM p_tb_mutasi_det_buku JOIN p_tb_subitem\n" +
"            ON p_tb_mutasi_det_buku.id_subitem=p_tb_subitem.id_subitem\n" +
"            WHERE id_item=@idKey\n" +
"        )AS mutBuku\n" +
"    JOIN p_tb_mutasi\n" +
"    ON mutBuku.id_mutasi=p_tb_mutasi.id_mutasi\n" +
"    WHERE tgl_mutasi<'[date]'\n" +
"    ORDER BY tgl_mutasi DESC\n" +
"    LIMIT 1\n" +
")AS stok\n" +
"FROM p_tb_item)AS qStockAll\n" +
"ON p_tb_item.id_item=qStockAll.id_item\n" +
"\n" +
"\n" +
"LEFT JOIN \n" +
"(\n" +
"    \n" +
"    SELECT \n" +
"    p_tb_item.id_item,\n" +
"    (IF(qMasuk.qty IS NOT NULL,qMasuk.qty,0)-IF(qKeluar.qty IS NOT NULL,qKeluar.qty,0)) AS stok\n" +
"    FROM p_tb_item\n" +
"    LEFT JOIN\n" +
"    (SELECT\n" +
"    qDet.id_item, \n" +
"    SUM(qDet.qty) qty\n" +
"    FROM\n" +
"    (SELECT p_tb_subitem.id_item, p_tb_mutasi_det_buku.qty FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_buku AS p_tb_mutasi_det_buku, bmd.p_tb_subitem AS p_tb_subitem, bmd.p_ref_jenis_mutasi AS p_ref_jenis_mutasi WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_buku.id_mutasi AND p_tb_subitem.id_subitem = p_tb_mutasi_det_buku.id_subitem AND p_ref_jenis_mutasi.id_jenis_mutasi = p_tb_mutasi.id_jenis_mutasi AND p_tb_mutasi.tgl_mutasi < '[date]' AND p_ref_jenis_mutasi.debit = '1' AND p_tb_mutasi.id_bidang = '[idBidang]' AND p_tb_mutasi.approved = '1') AS qDet\n" +
"    GROUP BY id_item) AS qMasuk\n" +
"    ON p_tb_item.id_item=qMasuk.id_item\n" +
"\n" +
"    LEFT JOIN\n" +
"    (SELECT\n" +
"    qDet.id_item, \n" +
"    SUM(qDet.qty) qty\n" +
"    FROM\n" +
"    (SELECT p_tb_subitem.id_item, p_tb_mutasi_det_buku.qty FROM bmd.p_tb_mutasi AS p_tb_mutasi, bmd.p_tb_mutasi_det_buku AS p_tb_mutasi_det_buku, bmd.p_tb_subitem AS p_tb_subitem, bmd.p_ref_jenis_mutasi AS p_ref_jenis_mutasi WHERE p_tb_mutasi.id_mutasi = p_tb_mutasi_det_buku.id_mutasi AND p_tb_subitem.id_subitem = p_tb_mutasi_det_buku.id_subitem AND p_ref_jenis_mutasi.id_jenis_mutasi = p_tb_mutasi.id_jenis_mutasi AND p_tb_mutasi.tgl_mutasi < '[date]' AND p_ref_jenis_mutasi.debit = '0' AND p_tb_mutasi.id_bidang = '[idBidang]' AND p_tb_mutasi.approved = '1') AS qDet\n" +
"    GROUP BY id_item) AS qKeluar\n" +
"    ON p_tb_item.id_item=qKeluar.id_item\n" +
"    \n" +
")AS qStockBidang\n" +
"ON p_tb_item.id_item=qStockBidang.id_item\n" +
"\n" +
"\n" +
"JOIN p_ref_kat\n" +
"ON p_tb_item.id_kat=p_ref_kat.id_kat";
        
        return tmp.replace("[date]", date).replace("[idBidang]", idBidang);
    }

*/

    public static String qPegawai="SELECT p_tb_pegawai.id_pegawai, p_tb_pegawai.nm_pegawai, p_tb_pegawai.nip_pegawai, p_tb_pegawai.jab_pegawai, p_tb_pegawai.id_bidang, p_tb_bidang.nm_bidang, p_tb_pegawai.aktif FROM bmd.p_tb_bidang AS p_tb_bidang, bmd.p_tb_pegawai AS p_tb_pegawai WHERE p_tb_bidang.id_bidang = p_tb_pegawai.id_bidang";
    
    public static String qPegawaiAktif="SELECT p_tb_pegawai.id_pegawai, p_tb_pegawai.nm_pegawai, p_tb_pegawai.nip_pegawai, p_tb_pegawai.jab_pegawai, p_tb_pegawai.id_bidang, p_tb_bidang.nm_bidang, p_tb_pegawai.aktif FROM bmd.p_tb_bidang AS p_tb_bidang, bmd.p_tb_pegawai AS p_tb_pegawai WHERE p_tb_bidang.id_bidang = p_tb_pegawai.id_bidang AND p_tb_pegawai.aktif='1'";
    
    public static String qBidang="select * from p_tb_bidang";
    
    public static String qPBJ="SELECT p_tb_pj_pbj.id_pj_pbj, p_tb_pj_pbj.nm_pj_pbj, p_tb_pj_pbj.nip_pj_pbj, p_tb_pj_pbj.id_jab_pbj, p_ref_jab_pbj.singkat_jab_pbj, p_ref_jab_pbj.nm_jab_pbj, p_tb_pj_pbj.id_bidang, p_tb_bidang.nm_bidang, p_tb_pj_pbj.no_sk_jab_pbj, p_tb_pj_pbj.tgl_sk_jab_pbj FROM bmd.p_tb_bidang AS p_tb_bidang, bmd.p_tb_pj_pbj AS p_tb_pj_pbj, bmd.p_ref_jab_pbj AS p_ref_jab_pbj WHERE p_tb_bidang.id_bidang = p_tb_pj_pbj.id_bidang AND p_ref_jab_pbj.id_jab_pbj = p_tb_pj_pbj.id_jab_pbj";
    
    public static String qTotal(String idMutasi){
        String tmp="SELECT\n" +
"SUM(real_qty*real_harga_penerimaan)subtotal\n" +
"FROM \n" +
"p_tb_mutasi_det_real\n" +
"WHERE id_mutasi='[ID_MUTASI]'\n" +
"GROUP BY id_mutasi";
        return tmp.replace("[ID_MUTASI]", idMutasi);
    }
    
    public static String qKat="SELECT \n" +
"p_ref_kat.*,\n" +
"p_ref_tipe.ket_tipe\n" +
"FROM p_ref_kat,p_ref_tipe WHERE p_ref_kat.id_tipe=p_ref_tipe.id_tipe";
    
    public static String qSubLastBuku(String idItem, String date, String curIdMutasi){
        String q="select\n" +
                "p_q_mutasi_det_buku.id_subitem,\n" +
                "p_q_mutasi_det_buku.stok_akhir\n" +
                "from\n" +
                "(select \n" +
                "id_mutasi,\n" +
                "tgl_mutasi,\n" +
                "wkt_masuk_item \n" +
                "from \n" +
                "p_q_mutasi_det_buku\n" +
                "where \n" +
                "id_item='"+idItem+"' and\n" +
                "tgl_mutasi<='"+date+"' and \n" +
                "not (id_mutasi ='"+curIdMutasi+"')\n" +
                "group by id_mutasi\n" +
                "order by tgl_mutasi desc,id_mutasi desc\n" +
                "limit 1)qMut\n" +
                "join p_q_mutasi_det_buku on qMut.id_mutasi=p_q_mutasi_det_buku.id_mutasi\n" +
                "where p_q_mutasi_det_buku.id_item='"+idItem+"' and p_q_mutasi_det_buku.stok_akhir>'0'\n" +
                "order by p_q_mutasi_det_buku.wkt_masuk_item asc";
        return q;
    }
    
    public static String qRptMutasiMaster(String date0, String date1){
        try {
            JMDate d0=JMDate.create(date0);
            JMDate d1=JMDate.create(date1);
            String txt0=d0.dateFull();
            String txt1=d1.dateFull();
            String m0=d0.getMonthString();
            String m1=d1.getMonthString();
            String ret="select \n" +
"('Gudang Utama') as gudang,\n" +
"(@pAwalTxt:='"+txt0+"') as periode_awal_txt,\n" +
"(@pAkhirTxt:='"+txt1+"') as periode_akhir_txt,\n" +
"(@pBlnAwalTxt:='"+m0+"') as bln_periode_awal_txt,\n" +
"(@pBlnAkhirTxt:='"+m1+"') as bln_periode_akhir_txt,\n" +
"(@pAwal:=cast('"+date0+"' as datetime)) as periode_awal,\n" +
"(@pAkhir:=cast('"+date1+"' as datetime)) as periode_akhir,\n" +
"(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"(\n" +
"	if(\n" +
"		(year(@pAkhir) - year(@pAwal))=0,\n" +
"		cast(year(@pAkhir) as varchar(100)),\n" +
"		concat(cast(year(@pAwal) as varchar(100)),'/',cast(year(@pAkhir) as varchar(100)))\n" +
"	)\n" +
") as ta,\n" +
"(\n" +
"	if(\n" +
"		(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=11,\n" +
"		concat(@pAwalTxt,' s/d ',@pAkhirTxt),\n" +
"		if(\n" +
"			(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=5,\n" +
"			if(\n" +
"				month(@pAwal)=1 and month(@pAkhir)=6 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"				'SEMESTER-I',\n" +
"				if(\n" +
"					month(@pAwal)=7 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"					'SEMESTER-II',\n" +
"					concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"				) \n" +
"			),\n" +
"			if(\n" +
"				(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=2,\n" +
"				if(\n" +
"					month(@pAwal)=1 and month(@pAkhir)=3 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"					'TRIWULAN-I',\n" +
"					if(\n" +
"						month(@pAwal)=4 and month(@pAkhir)=6 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"						'TRIWULAN-II',\n" +
"						if(\n" +
"							month(@pAwal)=7 and month(@pAkhir)=9 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"							'TRIWULAN-III',\n" +
"							if(\n" +
"								month(@pAwal)=10 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"								'TRIWULAN-IV',\n" +
"								concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"							)\n" +
"						)\n" +
"					)\n" +
"				),\n" +
"				if(\n" +
"					(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=0,\n" +
"					if(\n" +
"						day(@pAwal)=1 and day(last_day(@pAwal))=day(@pAkhir) and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"						concat('BULAN ',@pBlnAwalTxt),\n" +
"						concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"					),\n" +
"					concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"				)\n" +
"			)\n" +
"		)\n" +
"	)\n" +
") as periode,\n" +
"if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"jab_pengurus_brg,\n" +
"nm_pengurus_brg,\n" +
"nip_pengurus_brg\n" +
"from\n" +
"(select \n" +
"*\n" +
"from \n" +
"p_q_mutasi_det_buku\n" +
"where \n" +
"tgl_mutasi >=@pAwal and \n" +
"tgl_mutasi <=@pAkhir\n" +
"order by \n" +
"tgl_mutasi desc,\n" +
"id_mutasi desc\n" +
"limit 1)qDet";
            return ret;
        } catch (ParseException ex) {
            Logger.getLogger(QueryHelperPersediaan.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public static String qRptMutasi(String date0, String date1){
        try {
            JMDate d0=JMDate.create(date0);
            JMDate d1=JMDate.create(date1);
            String txt0=d0.dateFull();
            String txt1=d1.dateFull();
            String m0=d0.getMonthString();
            String m1=d1.getMonthString();
            
            String ret="select \n" +
"id_item ,\n" +
"id_kat ,\n" +
"ket_kat ,\n" +
"nm_item ,\n" +
"satuan ,\n" +
"gudang,\n" +
"ta,\n" +
"periode,\n" +
"jab_pengguna_brg,\n" +
"nm_pengguna_brg,\n" +
"nip_pengguna_brg,\n" +
"jab_pengurus_brg,\n" +
"nm_pengurus_brg,\n" +
"nip_pengurus_brg,\n" +
"if(qty_awal is not null,qty_awal,0) as qty_awal,\n" +
"if(sat_awal is not null,sat_awal,'-') as sat_awal,\n" +
"if(total_awal is not null,total_awal,0) as total_awal,\n" +
"if(qty_masuk is not null,qty_masuk,0) as qty_masuk,\n" +
"if(sat_masuk is not null,sat_masuk,'-') as sat_masuk,\n" +
"if(total_masuk is not null,total_masuk,0) as total_masuk,\n" +
"if(qty_keluar is not null,qty_keluar,0) as qty_keluar,\n" +
"if(sat_keluar is not null,sat_keluar,'-') as sat_keluar,\n" +
"if(total_keluar is not null,total_keluar,0) as total_keluar,\n" +
"if(qty_akhir is not null,qty_akhir,0) as qty_akhir,\n" +
"if(sat_akhir is not null,sat_akhir,'-') as sat_akhir,\n" +
"if(total_akhir is not null,total_akhir,0) as total_akhir\n" +
"from\n" +
"(select \n" +
"*\n" +
"from\n" +
"(select \n" +
"p_q_item.id_item ,\n" +
"p_q_item.id_kat ,\n" +
"p_q_item.ket_kat ,\n" +
"p_q_item.nm_item ,\n" +
"p_q_item.satuan ,\n" +
"('Gudang Utama') as gudang,\n" +
"(@pAwalTxt:='"+txt0+"') as periode_awal_txt,\n" +
"(@pAkhirTxt:='"+txt1+"') as periode_akhir_txt,\n" +
"(@pBlnAwalTxt:='"+m0+"') as bln_periode_awal_txt,\n" +
"(@pBlnAkhirTxt:='"+m1+"') as bln_periode_akhir_txt,\n" +
"(@pAwal:=cast('"+date0+"' as datetime)) as periode_awal,\n" +
"(@pAkhir:=cast('"+date1+"' as datetime)) as periode_akhir,\n" +
"(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"(\n" +
"	if(\n" +
"		(year(@pAkhir) - year(@pAwal))=0,\n" +
"		cast(year(@pAkhir) as varchar(100)),\n" +
"		concat(cast(year(@pAwal) as varchar(100)),'/',cast(year(@pAkhir) as varchar(100)))\n" +
"	)\n" +
") as ta,\n" +
"(\n" +
"	if(\n" +
"		(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=11,\n" +
"		if(\n" +
"			month(@pAwal)=1 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"			'',\n" +
"			concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt) \n" +
"		),\n" +
"		if(\n" +
"			(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=5,\n" +
"			if(\n" +
"				month(@pAwal)=1 and month(@pAkhir)=6 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"				'SEMESTER-I',\n" +
"				if(\n" +
"					month(@pAwal)=7 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"					'SEMESTER-II',\n" +
"					concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"				) \n" +
"			),\n" +
"			if(\n" +
"				(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=2,\n" +
"				if(\n" +
"					month(@pAwal)=1 and month(@pAkhir)=3 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"					'TRIWULAN-I',\n" +
"					if(\n" +
"						month(@pAwal)=4 and month(@pAkhir)=6 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"						'TRIWULAN-II',\n" +
"						if(\n" +
"							month(@pAwal)=7 and month(@pAkhir)=9 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"							'TRIWULAN-III',\n" +
"							if(\n" +
"								month(@pAwal)=10 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"								'TRIWULAN-IV',\n" +
"								concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"							)\n" +
"						)\n" +
"					)\n" +
"				),\n" +
"				if(\n" +
"					(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=0,\n" +
"					if(\n" +
"						day(@pAwal)=1 and day(last_day(@pAwal))=day(@pAkhir) and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"						concat('BULAN ',@pBlnAwalTxt),\n" +
"						concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"					),\n" +
"					concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhirTxt)\n" +
"				)\n" +
"			)\n" +
"		)\n" +
"	)\n" +
") as periode,\n" +
"(select if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as jab_pengguna_brg,\n" +
"(select if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nm_pengguna_brg,\n" +
"(select if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nip_pengguna_brg,\n" +
"(select jab_pengurus_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as jab_pengurus_brg,\n" +
"(select nm_pengurus_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nm_pengurus_brg,\n" +
"(select nip_pengurus_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nip_pengurus_brg,\n" +
"qqAwal.qty as qty_awal,\n" +
"qqAwal.sat_qty as sat_awal,\n" +
"qqAwal.total_qty as total_awal,\n" +
"qqMasuk.qty as qty_masuk,\n" +
"qqMasuk.sat_qty as sat_masuk,\n" +
"qqMasuk.total_qty as total_masuk,\n" +
"qqKeluar.qty as qty_keluar,\n" +
"qqKeluar.sat_qty as sat_keluar,\n" +
"qqKeluar.total_qty as total_keluar,\n" +
"qqAkhir.qty as qty_akhir,\n" +
"qqAkhir.sat_qty as sat_akhir,\n" +
"qqAkhir.total_qty as total_akhir\n" +
"from \n" +
"p_q_item\n" +
"left join\n" +
"	(\n" +
"		select \n" +
"		qqItem.id_item,\n" +
"		qqDet.qty,\n" +
"		qqDet.sat_qty,\n" +
"		qqDet.total_qty\n" +
"		from \n" +
"			(\n" +
"				select\n" +
"				*\n" +
"				from\n" +
"				(select \n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				from\n" +
"				(select\n" +
"				*\n" +
"				from\n" +
"				p_q_mutasi_det_buku\n" +
"				where\n" +
"				tgl_mutasi <=@p0\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qDet\n" +
"				group by\n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qItem\n" +
"				group by id_item\n" +
"			)qqItem\n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				sum(qty) as qty ,\n" +
"				group_concat(sat_qty separator ' +\\n') as sat_qty, \n" +
"				sum(total_qty) as total_qty\n" +
"				from\n" +
"				(select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				stok_akhir as qty ,\n" +
"				CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"				(stok_akhir *harga_satuan) as total_qty\n" +
"				from \n" +
"				p_q_mutasi_det_buku\n" +
"				where \n" +
"				tgl_mutasi <=@p0\n" +
"				order by wkt_masuk_item asc\n" +
"				)qDet\n" +
"				group by \n" +
"				id_mutasi,\n" +
"				id_item\n" +
"			)qqDet\n" +
"		on qqItem.id_item=qqDet.id_item and qqItem.id_mutasi=qqDet.id_mutasi\n" +
"	)qqAwal on p_q_item.id_item =qqAwal.id_item\n" +
"left join \n" +
"	(\n" +
"		select\n" +
"		id_item ,\n" +
"		sum(qty) as qty ,\n" +
"		group_concat(sat_qty separator ' +\\n')as sat_qty,\n" +
"		sum(total_qty)as total_qty \n" +
"		from\n" +
"		(select\n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		id_subitem ,\n" +
"		harga_satuan ,\n" +
"		qty ,\n" +
"		CONCAT(FORMAT(qty,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"		(qty*harga_satuan) as total_qty\n" +
"		from\n" +
"		p_q_mutasi_det_buku\n" +
"		where\n" +
"		debit ='1' and\n" +
"		qty >'0' and\n" +
"		tgl_mutasi >=@pAwal and \n" +
"		tgl_mutasi <=@pAkhir \n" +
"		order by \n" +
"		id_mutasi asc,\n" +
"		wkt_masuk_item asc)qDet\n" +
"		group by \n" +
"		id_item \n" +
"	)qqMasuk on p_q_item.id_item =qqMasuk.id_item\n" +
"left join \n" +
"	(\n" +
"		select\n" +
"		id_item ,\n" +
"		sum(qty) as qty ,\n" +
"		group_concat(sat_qty separator ' +\\n')as sat_qty,\n" +
"		sum(total_qty)as total_qty \n" +
"		from\n" +
"		(select\n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		id_subitem ,\n" +
"		harga_satuan ,\n" +
"		qty ,\n" +
"		CONCAT(FORMAT(qty,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"		(qty*harga_satuan) as total_qty\n" +
"		from\n" +
"		p_q_mutasi_det_buku\n" +
"		where\n" +
"		debit ='0' and\n" +
"		qty >'0' and\n" +
"		tgl_mutasi >=@pAwal and \n" +
"		tgl_mutasi <=@pAkhir \n" +
"		order by \n" +
"		id_mutasi asc,\n" +
"		wkt_masuk_item asc)qDet\n" +
"		group by \n" +
"		id_item \n" +
"	)qqKeluar on p_q_item.id_item =qqKeluar.id_item\n" +
"left join \n" +
"	(\n" +
"		select \n" +
"		qqItem.id_item,\n" +
"		qqDet.qty,\n" +
"		qqDet.sat_qty,\n" +
"		qqDet.total_qty\n" +
"		from \n" +
"			(\n" +
"				select\n" +
"				*\n" +
"				from\n" +
"				(select \n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				from\n" +
"				(select\n" +
"				*\n" +
"				from\n" +
"				p_q_mutasi_det_buku\n" +
"				where\n" +
"				tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qDet\n" +
"				group by\n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qItem\n" +
"				group by id_item\n" +
"			)qqItem\n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				sum(qty) as qty ,\n" +
"				group_concat(sat_qty separator ' +\\n') as sat_qty, \n" +
"				sum(total_qty) as total_qty\n" +
"				from\n" +
"				(select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				stok_akhir as qty ,\n" +
"				CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"				(stok_akhir *harga_satuan) as total_qty\n" +
"				from \n" +
"				p_q_mutasi_det_buku\n" +
"				where \n" +
"				tgl_mutasi <=@pAkhir\n" +
"				order by wkt_masuk_item asc\n" +
"				)qDet\n" +
"				group by \n" +
"				id_mutasi,\n" +
"				id_item\n" +
"			)qqDet\n" +
"		on qqItem.id_item=qqDet.id_item and qqItem.id_mutasi=qqDet.id_mutasi\n" +
"	)qqAkhir on p_q_item.id_item =qqAkhir.id_item\n" +
")qFilter\n" +
"where \n" +
"(qty_awal>0 or qty_masuk>0 or qty_keluar>0) and \n" +
"(qty_awal is not null or qty_masuk is not null or qty_keluar is not null)\n" +
")qFinal order by nm_item asc";
            return ret;
        } catch (ParseException ex) {
            return "";
        }
    }
    
    public static String qTmp="select \n" +
"id_item ,\n" +
"id_kat ,\n" +
"ket_kat ,\n" +
"nm_item ,\n" +
"satuan ,\n" +
"gudang,\n" +
"ta,\n" +
"periode,\n" +
"jab_pengguna_brg,\n" +
"nm_pengguna_brg,\n" +
"nip_pengguna_brg,\n" +
"jab_pengurus_brg,\n" +
"nm_pengurus_brg,\n" +
"nip_pengurus_brg,\n" +
"if(qty_awal is not null,qty_awal,0) as qty_awal,\n" +
"if(sat_awal is not null,sat_awal,'-') as sat_awal,\n" +
"if(total_awal is not null,total_awal,0) as total_awal,\n" +
"if(qty_masuk is not null,qty_masuk,0) as qty_masuk,\n" +
"if(sat_masuk is not null,sat_masuk,'-') as sat_masuk,\n" +
"if(total_masuk is not null,total_masuk,0) as total_masuk,\n" +
"if(qty_keluar is not null,qty_keluar,0) as qty_keluar,\n" +
"if(sat_keluar is not null,sat_keluar,'-') as sat_keluar,\n" +
"if(total_keluar is not null,total_keluar,0) as total_keluar,\n" +
"if(qty_akhir is not null,qty_akhir,0) as qty_akhir,\n" +
"if(sat_akhir is not null,sat_akhir,'-') as sat_akhir,\n" +
"if(total_akhir is not null,total_akhir,0) as total_akhir\n" +
"from\n" +
"(select \n" +
"*\n" +
"from\n" +
"(select \n" +
"p_q_item.id_item ,\n" +
"p_q_item.id_kat ,\n" +
"p_q_item.ket_kat ,\n" +
"p_q_item.nm_item ,\n" +
"p_q_item.satuan ,\n" +
"('Gudang Utama') as gudang,\n" +
"(@pAwalTxt:='AWAL') as periode_awal_txt,\n" +
"(@pAkhirTxt:='AKHIR') as periode_akhir_txt,\n" +
"(@pBlnAwalTxt:='BLN AWAL') as bln_periode_awal_txt,\n" +
"(@pBlnAkhirTxt:='BLN AKHIR') as bln_periode_akhir_txt,\n" +
"(@pAwal:=cast('2020-01-01 00:00:00' as datetime)) as periode_awal,\n" +
"(@pAkhir:=cast('2020-12-31 23:59:59' as datetime)) as periode_akhir,\n" +
"(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"(\n" +
"	if(\n" +
"		(year(@pAkhir) - year(@pAwal))=0,\n" +
"		cast(year(@pAkhir) as varchar(100)),\n" +
"		concat(cast(year(@pAwal) as varchar(100)),'/',cast(year(@pAkhir) as varchar(100)))\n" +
"	)\n" +
") as ta,\n" +
"(\n" +
"	if(\n" +
"		(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=11,\n" +
"		if(\n" +
"			month(@pAwal)=1 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"			'',\n" +
"			concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhir) \n" +
"		),\n" +
"		if(\n" +
"			(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=5,\n" +
"			if(\n" +
"				month(@pAwal)=1 and month(@pAkhir)=6 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"				'SEMESTER-I',\n" +
"				if(\n" +
"					month(@pAwal)=7 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"					'SEMESTER-II',\n" +
"					concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhir)\n" +
"				) \n" +
"			),\n" +
"			if(\n" +
"				(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=2,\n" +
"				if(\n" +
"					month(@pAwal)=1 and month(@pAkhir)=3 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"					'TRIWULAN-I',\n" +
"					if(\n" +
"						month(@pAwal)=4 and month(@pAkhir)=6 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"						'TRIWULAN-II',\n" +
"						if(\n" +
"							month(@pAwal)=7 and month(@pAkhir)=9 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"							'TRIWULAN-III',\n" +
"							if(\n" +
"								month(@pAwal)=10 and month(@pAkhir)=12 and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"								'TRIWULAN-IV',\n" +
"								concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhir)\n" +
"							)\n" +
"						)\n" +
"					)\n" +
"				),\n" +
"				if(\n" +
"					(12*(year(@pAkhir) - year(@pAwal))+month(@pAkhir)-month(@pAwal))=0,\n" +
"					if(\n" +
"						day(@pAwal)=1 and day(last_day(@pAwal))=day(@pAkhir) and hour(@pAwal)=0 and hour(@pAkhir)=23 and minute(@pAwal)=0 and minute(@pAkhir)=59 and second(@pAwal)=0 and second(@pAkhir)=59,\n" +
"						concat('BULAN ',@pBlnAwalTxt),\n" +
"						concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhir)\n" +
"					),\n" +
"					concat('PERIODE ',@pAwalTxt,' s/d ',@pAkhir)\n" +
"				)\n" +
"			)\n" +
"		)\n" +
"	)\n" +
") as periode,\n" +
"(select if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as jab_pengguna_brg,\n" +
"(select if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nm_pengguna_brg,\n" +
"(select if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nip_pengguna_brg,\n" +
"(select jab_pengurus_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as jab_pengurus_brg,\n" +
"(select nm_pengurus_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nm_pengurus_brg,\n" +
"(select nip_pengurus_brg from p_q_mutasi_det_buku where tgl_mutasi<=@pAkhir order by tgl_mutasi desc, id_mutasi desc limit 1) as nip_pengurus_brg,\n" +
"qqAwal.qty as qty_awal,\n" +
"qqAwal.sat_qty as sat_awal,\n" +
"qqAwal.total_qty as total_awal,\n" +
"qqMasuk.qty as qty_masuk,\n" +
"qqMasuk.sat_qty as sat_masuk,\n" +
"qqMasuk.total_qty as total_masuk,\n" +
"qqKeluar.qty as qty_keluar,\n" +
"qqKeluar.sat_qty as sat_keluar,\n" +
"qqKeluar.total_qty as total_keluar,\n" +
"qqAkhir.qty as qty_akhir,\n" +
"qqAkhir.sat_qty as sat_akhir,\n" +
"qqAkhir.total_qty as total_akhir\n" +
"from \n" +
"p_q_item\n" +
"left join\n" +
"	(\n" +
"		select \n" +
"		qqItem.id_item,\n" +
"		qqDet.qty,\n" +
"		qqDet.sat_qty,\n" +
"		qqDet.total_qty\n" +
"		from \n" +
"			(\n" +
"				select\n" +
"				*\n" +
"				from\n" +
"				(select \n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				from\n" +
"				(select\n" +
"				*\n" +
"				from\n" +
"				p_q_mutasi_det_buku\n" +
"				where\n" +
"				tgl_mutasi <=@p0\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qDet\n" +
"				group by\n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qItem\n" +
"				group by id_item\n" +
"			)qqItem\n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				sum(qty) as qty ,\n" +
"				group_concat(sat_qty separator ' +\\n') as sat_qty, \n" +
"				sum(total_qty) as total_qty\n" +
"				from\n" +
"				(select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				stok_akhir as qty ,\n" +
"				CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"				(stok_akhir *harga_satuan) as total_qty\n" +
"				from \n" +
"				p_q_mutasi_det_buku\n" +
"				where \n" +
"				tgl_mutasi <=@p0\n" +
"				order by wkt_masuk_item asc\n" +
"				)qDet\n" +
"				group by \n" +
"				id_mutasi,\n" +
"				id_item\n" +
"			)qqDet\n" +
"		on qqItem.id_item=qqDet.id_item and qqItem.id_mutasi=qqDet.id_mutasi\n" +
"	)qqAwal on p_q_item.id_item =qqAwal.id_item\n" +
"left join \n" +
"	(\n" +
"		select\n" +
"		id_item ,\n" +
"		sum(qty) as qty ,\n" +
"		group_concat(sat_qty separator ' +\\n')as sat_qty,\n" +
"		sum(total_qty)as total_qty \n" +
"		from\n" +
"		(select\n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		id_subitem ,\n" +
"		harga_satuan ,\n" +
"		qty ,\n" +
"		CONCAT(FORMAT(qty,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"		(qty*harga_satuan) as total_qty\n" +
"		from\n" +
"		p_q_mutasi_det_buku\n" +
"		where\n" +
"		debit ='1' and\n" +
"		qty >'0' and\n" +
"		tgl_mutasi >=@pAwal and \n" +
"		tgl_mutasi <=@pAkhir \n" +
"		order by \n" +
"		id_mutasi asc,\n" +
"		wkt_masuk_item asc)qDet\n" +
"		group by \n" +
"		id_item \n" +
"	)qqMasuk on p_q_item.id_item =qqMasuk.id_item\n" +
"left join \n" +
"	(\n" +
"		select\n" +
"		id_item ,\n" +
"		sum(qty) as qty ,\n" +
"		group_concat(sat_qty separator ' +\\n')as sat_qty,\n" +
"		sum(total_qty)as total_qty \n" +
"		from\n" +
"		(select\n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		id_subitem ,\n" +
"		harga_satuan ,\n" +
"		qty ,\n" +
"		CONCAT(FORMAT(qty,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"		(qty*harga_satuan) as total_qty\n" +
"		from\n" +
"		p_q_mutasi_det_buku\n" +
"		where\n" +
"		debit ='0' and\n" +
"		qty >'0' and\n" +
"		tgl_mutasi >=@pAwal and \n" +
"		tgl_mutasi <=@pAkhir \n" +
"		order by \n" +
"		id_mutasi asc,\n" +
"		wkt_masuk_item asc)qDet\n" +
"		group by \n" +
"		id_item \n" +
"	)qqKeluar on p_q_item.id_item =qqKeluar.id_item\n" +
"left join \n" +
"	(\n" +
"		select \n" +
"		qqItem.id_item,\n" +
"		qqDet.qty,\n" +
"		qqDet.sat_qty,\n" +
"		qqDet.total_qty\n" +
"		from \n" +
"			(\n" +
"				select\n" +
"				*\n" +
"				from\n" +
"				(select \n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				from\n" +
"				(select\n" +
"				*\n" +
"				from\n" +
"				p_q_mutasi_det_buku\n" +
"				where\n" +
"				tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qDet\n" +
"				group by\n" +
"				id_item,\n" +
"				id_mutasi\n" +
"				order by \n" +
"				id_item asc,\n" +
"				tgl_mutasi desc,\n" +
"				id_mutasi desc)qItem\n" +
"				group by id_item\n" +
"			)qqItem\n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				sum(qty) as qty ,\n" +
"				group_concat(sat_qty separator ' +\\n') as sat_qty, \n" +
"				sum(total_qty) as total_qty\n" +
"				from\n" +
"				(select \n" +
"				id_mutasi ,\n" +
"				tgl_mutasi ,\n" +
"				id_item ,\n" +
"				id_subitem ,\n" +
"				harga_satuan ,\n" +
"				stok_akhir as qty ,\n" +
"				CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"				(stok_akhir *harga_satuan) as total_qty\n" +
"				from \n" +
"				p_q_mutasi_det_buku\n" +
"				where \n" +
"				tgl_mutasi <=@pAkhir\n" +
"				order by wkt_masuk_item asc\n" +
"				)qDet\n" +
"				group by \n" +
"				id_mutasi,\n" +
"				id_item\n" +
"			)qqDet\n" +
"		on qqItem.id_item=qqDet.id_item and qqItem.id_mutasi=qqDet.id_mutasi\n" +
"	)qqAkhir on p_q_item.id_item =qqAkhir.id_item\n" +
")qFilter\n" +
"where \n" +
"(qty_awal>0 or qty_masuk>0 or qty_keluar>0) and \n" +
"(qty_awal is not null or qty_masuk is not null or qty_keluar is not null)\n" +
")qFinal";
    
    
    
}

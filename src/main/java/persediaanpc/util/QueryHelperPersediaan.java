package persediaanpc.util;

import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMFormatCollection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryHelperPersediaan {
    
    public static String qListPengadaanFilter(int tahun){
        String date0=tahun+"-01-01 00:00:00";
        String date1=tahun+"-12-31 23:59:59";
        
        String ret="SELECT\n" +
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
"WHERE debit='1'and tgl_mutasi>='"+date0+"' and tgl_mutasi<='"+date1+"') AS det\n" +
"GROUP BY id_mutasi\n" +
"ORDER BY tgl_mutasi asc";
        
        
        return ret;
    }
    
    
    public static String qListPermintaanFilter(int tahun){
        String date0=tahun+"-01-01 00:00:00";
        String date1=tahun+"-12-31 23:59:59";
        
        String ret="SELECT\n" +
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
"WHERE debit='0'and tgl_mutasi>='"+date0+"' and tgl_mutasi<='"+date1+"') AS det\n" +
"GROUP BY id_mutasi\n" +
"ORDER BY tgl_mutasi asc";
        
        
        return ret;
    }
    
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
"join \n" +
"	(\n" +
"		select \n" +
"		pti.id_item ,\n" +
"		pti.nm_item ,\n" +
"		pti.satuan ,\n" +
"		pti.id_kat ,\n" +
"		p_q_ref_kat.ket_kat ,\n" +
"		p_q_ref_kat.id_tipe ,\n" +
"		p_q_ref_kat.ket_tipe \n" +
"		from \n" +
"		p_tb_item pti \n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				prk .id_kat ,\n" +
"				prk .ket_kat ,\n" +
"				prk .id_tipe ,\n" +
"				prt .ket_tipe\n" +
"				from \n" +
"				p_ref_kat prk \n" +
"				join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"			)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"	)p_q_item on p_tb_mutasi_det_real.id_item=p_q_item.id_item\n" +
"join \n" +
"	(\n" +
"		select \n" +
"		ptm.id_mutasi ,\n" +
"		ptm.tgl_mutasi ,\n" +
"		ptm.no_ba_mutasi ,\n" +
"		ptm.id_jenis_mutasi ,\n" +
"		prjm.ket_jenis_mutasi ,\n" +
"		prjm.debit ,\n" +
"		prjm.struktur_ket ,\n" +
"		ptm.nm_pihak2 ,\n" +
"		ptm.nip_pihak2 ,\n" +
"		ptm.almt_pihak2 ,\n" +
"		ptm.jab_pihak2 ,\n" +
"		ptm.instansi_pihak2 ,\n" +
"		ptm.nm_pengguna_brg ,\n" +
"		ptm.nip_pengguna_brg ,\n" +
"		ptm.jab_pengguna_brg ,\n" +
"		ptm.nm_tu_brg ,\n" +
"		ptm.nip_tu_brg ,\n" +
"		ptm.jab_tu_brg ,\n" +
"		ptm.nm_pengurus_brg ,\n" +
"		ptm.nip_pengurus_brg ,\n" +
"		ptm.jab_pengurus_brg ,\n" +
"		ptm.no_dok_dasar ,\n" +
"		ptm.tgl_dok_dasar ,\n" +
"		ptm.no_dok_dasar2 ,\n" +
"		ptm.tgl_dok_dasar2 ,\n" +
"		ptm.id_bidang ,\n" +
"		ptb.nm_bidang ,\n" +
"		ptm.approved ,\n" +
"		ptm.printed \n" +
"		from \n" +
"		p_tb_mutasi ptm \n" +
"		join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"		join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"	)p_q_mutasi on p_tb_mutasi_det_real.id_mutasi=p_q_mutasi.id_mutasi\n" +
"where p_q_mutasi.approved='0' and p_tb_mutasi_det_real.excluded='0'\n" +
"order by tgl_mutasi asc, id_mutasi asc";
    
    public static String XXXqDetRealSorted="SELECT \n" +
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
"	(\n" +
"		select \n" +
"		pti.id_item ,\n" +
"		pti.nm_item ,\n" +
"		pti.satuan ,\n" +
"		pti.id_kat ,\n" +
"		p_q_ref_kat.ket_kat ,\n" +
"		p_q_ref_kat.id_tipe ,\n" +
"		p_q_ref_kat.ket_tipe \n" +
"		from \n" +
"		p_tb_item pti \n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				prk .id_kat ,\n" +
"				prk .ket_kat ,\n" +
"				prk .id_tipe ,\n" +
"				prt .ket_tipe\n" +
"				from \n" +
"				p_ref_kat prk \n" +
"				join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"			)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"	)p_q_item\n" +
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
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)p_q_mutasi_det_buku\n" +
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
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)p_q_mutasi_det_buku\n" +
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
"	(\n" +
"		select \n" +
"		ptmdb.id_det_mutasi_buku ,\n" +
"		ptmdb.id_mutasi ,\n" +
"		p_q_mutasi.tgl_mutasi ,\n" +
"		p_q_mutasi.no_ba_mutasi ,\n" +
"		p_q_mutasi.id_jenis_mutasi ,\n" +
"		p_q_mutasi.ket_jenis_mutasi ,\n" +
"		p_q_mutasi.debit ,\n" +
"		p_q_mutasi.struktur_ket ,\n" +
"		p_q_mutasi.nm_pihak2 ,\n" +
"		p_q_mutasi.nip_pihak2 ,\n" +
"		p_q_mutasi.almt_pihak2 ,\n" +
"		p_q_mutasi.jab_pihak2 ,\n" +
"		p_q_mutasi.instansi_pihak2 ,\n" +
"		p_q_mutasi.nm_pengguna_brg ,\n" +
"		p_q_mutasi.nip_pengguna_brg ,\n" +
"		p_q_mutasi.jab_pengguna_brg ,\n" +
"		p_q_mutasi.nm_tu_brg ,\n" +
"		p_q_mutasi.nip_tu_brg ,\n" +
"		p_q_mutasi.jab_tu_brg ,\n" +
"		p_q_mutasi.nm_pengurus_brg ,\n" +
"		p_q_mutasi.nip_pengurus_brg ,\n" +
"		p_q_mutasi.jab_pengurus_brg ,\n" +
"		p_q_mutasi.no_dok_dasar ,\n" +
"		p_q_mutasi.tgl_dok_dasar ,\n" +
"		p_q_mutasi.no_dok_dasar2 ,\n" +
"		p_q_mutasi.tgl_dok_dasar2 ,\n" +
"		p_q_mutasi.id_bidang ,\n" +
"		p_q_mutasi.nm_bidang ,\n" +
"		p_q_mutasi.approved ,\n" +
"		p_q_mutasi.printed ,\n" +
"		p_q_subitem.id_subitem ,\n" +
"		p_q_subitem.wkt_masuk_item ,\n" +
"		p_q_subitem.id_item ,\n" +
"		p_q_subitem.nm_item ,\n" +
"		p_q_subitem.satuan ,\n" +
"		p_q_subitem.id_kat ,\n" +
"		p_q_subitem.ket_kat ,\n" +
"		p_q_subitem.id_tipe ,\n" +
"		p_q_subitem.ket_tipe ,\n" +
"		p_q_subitem.harga_satuan ,\n" +
"		ptmdb.qty ,\n" +
"		ptmdb.stok_awal ,\n" +
"		ptmdb.stok_akhir \n" +
"		from \n" +
"		p_tb_mutasi_det_buku ptmdb \n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				ptm.id_mutasi ,\n" +
"				ptm.tgl_mutasi ,\n" +
"				ptm.no_ba_mutasi ,\n" +
"				ptm.id_jenis_mutasi ,\n" +
"				prjm.ket_jenis_mutasi ,\n" +
"				prjm.debit ,\n" +
"				prjm.struktur_ket ,\n" +
"				ptm.nm_pihak2 ,\n" +
"				ptm.nip_pihak2 ,\n" +
"				ptm.almt_pihak2 ,\n" +
"				ptm.jab_pihak2 ,\n" +
"				ptm.instansi_pihak2 ,\n" +
"				ptm.nm_pengguna_brg ,\n" +
"				ptm.nip_pengguna_brg ,\n" +
"				ptm.jab_pengguna_brg ,\n" +
"				ptm.nm_tu_brg ,\n" +
"				ptm.nip_tu_brg ,\n" +
"				ptm.jab_tu_brg ,\n" +
"				ptm.nm_pengurus_brg ,\n" +
"				ptm.nip_pengurus_brg ,\n" +
"				ptm.jab_pengurus_brg ,\n" +
"				ptm.no_dok_dasar ,\n" +
"				ptm.tgl_dok_dasar ,\n" +
"				ptm.no_dok_dasar2 ,\n" +
"				ptm.tgl_dok_dasar2 ,\n" +
"				ptm.id_bidang ,\n" +
"				ptb.nm_bidang ,\n" +
"				ptm.approved ,\n" +
"				ptm.printed \n" +
"				from \n" +
"				p_tb_mutasi ptm \n" +
"				join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"				join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"			)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"		JOIN \n" +
"			(\n" +
"				select \n" +
"				pts.id_subitem ,\n" +
"				pts.wkt_masuk_item ,\n" +
"				pts.id_item ,\n" +
"				p_q_item.nm_item ,\n" +
"				p_q_item.satuan ,\n" +
"				p_q_item.id_kat ,\n" +
"				p_q_item.ket_kat ,\n" +
"				p_q_item.id_tipe ,\n" +
"				p_q_item.ket_tipe ,\n" +
"				pts.harga_satuan \n" +
"				from \n" +
"				p_tb_subitem pts \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						pti.id_item ,\n" +
"						pti.nm_item ,\n" +
"						pti.satuan ,\n" +
"						pti.id_kat ,\n" +
"						p_q_ref_kat.ket_kat ,\n" +
"						p_q_ref_kat.id_tipe ,\n" +
"						p_q_ref_kat.ket_tipe \n" +
"						from \n" +
"						p_tb_item pti \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								prk .id_kat ,\n" +
"								prk .ket_kat ,\n" +
"								prk .id_tipe ,\n" +
"								prt .ket_tipe\n" +
"								from \n" +
"								p_ref_kat prk \n" +
"								join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"							)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"					)p_q_item on pts.id_item =p_q_item.id_item \n" +
"			)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"	)pqmdb\n" +
"where \n" +
"id_item='"+idItem+"' and\n" +
"tgl_mutasi<='"+date+"' and \n" +
"not (id_mutasi ='"+curIdMutasi+"')\n" +
"group by id_mutasi\n" +
"order by tgl_mutasi desc,id_mutasi desc\n" +
"limit 1)qMut\n" +
"join \n" +
"	(\n" +
"		select \n" +
"		ptmdb.id_det_mutasi_buku ,\n" +
"		ptmdb.id_mutasi ,\n" +
"		p_q_mutasi.tgl_mutasi ,\n" +
"		p_q_mutasi.no_ba_mutasi ,\n" +
"		p_q_mutasi.id_jenis_mutasi ,\n" +
"		p_q_mutasi.ket_jenis_mutasi ,\n" +
"		p_q_mutasi.debit ,\n" +
"		p_q_mutasi.struktur_ket ,\n" +
"		p_q_mutasi.nm_pihak2 ,\n" +
"		p_q_mutasi.nip_pihak2 ,\n" +
"		p_q_mutasi.almt_pihak2 ,\n" +
"		p_q_mutasi.jab_pihak2 ,\n" +
"		p_q_mutasi.instansi_pihak2 ,\n" +
"		p_q_mutasi.nm_pengguna_brg ,\n" +
"		p_q_mutasi.nip_pengguna_brg ,\n" +
"		p_q_mutasi.jab_pengguna_brg ,\n" +
"		p_q_mutasi.nm_tu_brg ,\n" +
"		p_q_mutasi.nip_tu_brg ,\n" +
"		p_q_mutasi.jab_tu_brg ,\n" +
"		p_q_mutasi.nm_pengurus_brg ,\n" +
"		p_q_mutasi.nip_pengurus_brg ,\n" +
"		p_q_mutasi.jab_pengurus_brg ,\n" +
"		p_q_mutasi.no_dok_dasar ,\n" +
"		p_q_mutasi.tgl_dok_dasar ,\n" +
"		p_q_mutasi.no_dok_dasar2 ,\n" +
"		p_q_mutasi.tgl_dok_dasar2 ,\n" +
"		p_q_mutasi.id_bidang ,\n" +
"		p_q_mutasi.nm_bidang ,\n" +
"		p_q_mutasi.approved ,\n" +
"		p_q_mutasi.printed ,\n" +
"		p_q_subitem.id_subitem ,\n" +
"		p_q_subitem.wkt_masuk_item ,\n" +
"		p_q_subitem.id_item ,\n" +
"		p_q_subitem.nm_item ,\n" +
"		p_q_subitem.satuan ,\n" +
"		p_q_subitem.id_kat ,\n" +
"		p_q_subitem.ket_kat ,\n" +
"		p_q_subitem.id_tipe ,\n" +
"		p_q_subitem.ket_tipe ,\n" +
"		p_q_subitem.harga_satuan ,\n" +
"		ptmdb.qty ,\n" +
"		ptmdb.stok_awal ,\n" +
"		ptmdb.stok_akhir \n" +
"		from \n" +
"		p_tb_mutasi_det_buku ptmdb \n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				ptm.id_mutasi ,\n" +
"				ptm.tgl_mutasi ,\n" +
"				ptm.no_ba_mutasi ,\n" +
"				ptm.id_jenis_mutasi ,\n" +
"				prjm.ket_jenis_mutasi ,\n" +
"				prjm.debit ,\n" +
"				prjm.struktur_ket ,\n" +
"				ptm.nm_pihak2 ,\n" +
"				ptm.nip_pihak2 ,\n" +
"				ptm.almt_pihak2 ,\n" +
"				ptm.jab_pihak2 ,\n" +
"				ptm.instansi_pihak2 ,\n" +
"				ptm.nm_pengguna_brg ,\n" +
"				ptm.nip_pengguna_brg ,\n" +
"				ptm.jab_pengguna_brg ,\n" +
"				ptm.nm_tu_brg ,\n" +
"				ptm.nip_tu_brg ,\n" +
"				ptm.jab_tu_brg ,\n" +
"				ptm.nm_pengurus_brg ,\n" +
"				ptm.nip_pengurus_brg ,\n" +
"				ptm.jab_pengurus_brg ,\n" +
"				ptm.no_dok_dasar ,\n" +
"				ptm.tgl_dok_dasar ,\n" +
"				ptm.no_dok_dasar2 ,\n" +
"				ptm.tgl_dok_dasar2 ,\n" +
"				ptm.id_bidang ,\n" +
"				ptb.nm_bidang ,\n" +
"				ptm.approved ,\n" +
"				ptm.printed \n" +
"				from \n" +
"				p_tb_mutasi ptm \n" +
"				join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"				join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"			)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"		JOIN \n" +
"			(\n" +
"				select \n" +
"				pts.id_subitem ,\n" +
"				pts.wkt_masuk_item ,\n" +
"				pts.id_item ,\n" +
"				p_q_item.nm_item ,\n" +
"				p_q_item.satuan ,\n" +
"				p_q_item.id_kat ,\n" +
"				p_q_item.ket_kat ,\n" +
"				p_q_item.id_tipe ,\n" +
"				p_q_item.ket_tipe ,\n" +
"				pts.harga_satuan \n" +
"				from \n" +
"				p_tb_subitem pts \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						pti.id_item ,\n" +
"						pti.nm_item ,\n" +
"						pti.satuan ,\n" +
"						pti.id_kat ,\n" +
"						p_q_ref_kat.ket_kat ,\n" +
"						p_q_ref_kat.id_tipe ,\n" +
"						p_q_ref_kat.ket_tipe \n" +
"						from \n" +
"						p_tb_item pti \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								prk .id_kat ,\n" +
"								prk .ket_kat ,\n" +
"								prk .id_tipe ,\n" +
"								prt .ket_tipe\n" +
"								from \n" +
"								p_ref_kat prk \n" +
"								join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"							)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"					)p_q_item on pts.id_item =p_q_item.id_item \n" +
"			)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"	)p_q_mutasi_det_buku on qMut.id_mutasi=p_q_mutasi_det_buku.id_mutasi\n" +
"where p_q_mutasi_det_buku.id_item='"+idItem+"' and p_q_mutasi_det_buku.stok_akhir>'0'\n" +
"order by p_q_mutasi_det_buku.wkt_masuk_item asc";
        return q;
    }
    
    public static String qRptMutasiMaster(int tahun){
        try {
            List<JMDate> d0=new ArrayList();
            List<JMDate> d1=new ArrayList();
            List<String> txt0=new ArrayList();
            List<String> txt1=new ArrayList();
            List<String> m0=new ArrayList();
            List<String> m1=new ArrayList();
            List<String> per=new ArrayList();
            
            
            JMDate d0Tmp=JMDate.create(tahun+"-01-01 00:00:00");
            JMDate d1Tmp=JMDate.create(tahun+"-12-31 23:59:59");
            String txt0Tmp=d0Tmp.dateFullWithoutWeekday();
            String txt1Tmp=d1Tmp.dateFullWithoutWeekday();
            String m0Tmp=d0Tmp.getMonthString();
            String m1Tmp=d1Tmp.getMonthString();
            String pTmp=txt0Tmp+" s/d "+txt1Tmp;
            
            d0.add(d0Tmp);
            d1.add(d1Tmp);
            txt0.add(txt0Tmp);
            txt1.add(txt1Tmp);
            m0.add(m0Tmp);
            m1.add(m1Tmp);
            per.add(pTmp);
            
            d0Tmp=JMDate.create(tahun+"-01-01 00:00:00");
            d1Tmp=JMDate.create(tahun+"-06-30 23:59:59");
            txt0Tmp=d0Tmp.dateFullWithoutWeekday();
            txt1Tmp=d1Tmp.dateFullWithoutWeekday();
            m0Tmp=d0Tmp.getMonthString();
            m1Tmp=d1Tmp.getMonthString();
            pTmp="Semester-I";
            
            d0.add(d0Tmp);
            d1.add(d1Tmp);
            txt0.add(txt0Tmp);
            txt1.add(txt1Tmp);
            m0.add(m0Tmp);
            m1.add(m1Tmp);
            per.add(pTmp);
            
            d0Tmp=JMDate.create(tahun+"-07-01 00:00:00");
            d1Tmp=JMDate.create(tahun+"-12-31 23:59:59");
            txt0Tmp=d0Tmp.dateFullWithoutWeekday();
            txt1Tmp=d1Tmp.dateFullWithoutWeekday();
            m0Tmp=d0Tmp.getMonthString();
            m1Tmp=d1Tmp.getMonthString();
            pTmp="Semester-II";
            
            d0.add(d0Tmp);
            d1.add(d1Tmp);
            txt0.add(txt0Tmp);
            txt1.add(txt1Tmp);
            m0.add(m0Tmp);
            m1.add(m1Tmp);
            per.add(pTmp);
            
            for(int i=1;i<=12;i++){
                d0Tmp=JMDate.create(tahun+"-"+JMFormatCollection.leadingZero(i, 2)+"-01 00:00:00");
                d1Tmp=JMDate.create(tahun+"-"+JMFormatCollection.leadingZero(i, 2)+"-"+JMFormatCollection.leadingZero(d0Tmp.getMaxDayOfMonth(), 2)+" 23:59:59");
                txt0Tmp=d0Tmp.dateFullWithoutWeekday();
                txt1Tmp=d1Tmp.dateFullWithoutWeekday();
                m0Tmp=d0Tmp.getMonthString();
                m1Tmp=d1Tmp.getMonthString();
                pTmp="Bulan "+m0Tmp;

                d0.add(d0Tmp);
                d1.add(d1Tmp);
                txt0.add(txt0Tmp);
                txt1.add(txt1Tmp);
                m0.add(m0Tmp);
                m1.add(m1Tmp);
                per.add(pTmp);
            }
            
            
            String ret="select\n" +
"('Gudang Utama') as gudang,\n" +
"qUnion.periode_awal_txt,\n" +
"qUnion.periode_akhir_txt,\n" +
"qUnion.bln_periode_awal_txt,\n" +
"qUnion.bln_periode_akhir_txt,\n" +
"qUnion.periode_awal,\n" +
"qUnion.periode_akhir,\n" +
"qUnion.periode_0,\n" +
"qUnion.ta,\n" +
"qUnion.periode,\n" +
"qUnion.jab_pengguna_brg,\n" +
"qUnion.nm_pengguna_brg,\n" +
"qUnion.nip_pengguna_brg,\n" +
"qUnion.jab_pengurus_brg,\n" +
"qUnion.nm_pengurus_brg,\n" +
"qUnion.nip_pengurus_brg,\n" +
"qUnion.id\n" +
"from \n" +
"	(\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(0)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(0)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(0)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(0)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(0).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(0).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(0)+"') as periode,\n" +
"			('Tahunan') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(1)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(1)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(1)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(1)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(1).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(1).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(1)+"') as periode,\n" +
"			('"+per.get(1)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(2)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(2)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(2)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(2)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(2).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(2).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(2)+"') as periode,\n" +
"			('"+per.get(2)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(3)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(3)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(3)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(3)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(3).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(3).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(3)+"') as periode,\n" +
"			('"+m0.get(3)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(4)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(4)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(4)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(4)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(4).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(4).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(4)+"') as periode,\n" +
"			('"+m0.get(4)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(5)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(5)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(5)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(5)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(5).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(5).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(5)+"') as periode,\n" +
"			('"+m0.get(5)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(6)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(6)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(6)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(6)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(6).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(6).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(6)+"') as periode,\n" +
"			('"+m0.get(6)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(7)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(7)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(7)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(7)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(7).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(7).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(7)+"') as periode,\n" +
"			('"+m0.get(7)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(8)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(8)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(8)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(8)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(8).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(8).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(8)+"') as periode,\n" +
"			('"+m0.get(8)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(9)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(9)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(9)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(9)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(9).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(9).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(9)+"') as periode,\n" +
"			('"+m0.get(9)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(10)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(10)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(10)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(10)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(10).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(10).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(10)+"') as periode,\n" +
"			('"+m0.get(10)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(11)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(11)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(11)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(11)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(11).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(11).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(11)+"') as periode,\n" +
"			('"+m0.get(11)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(12)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(12)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(12)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(12)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(12).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(12).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(12)+"') as periode,\n" +
"			('"+m0.get(12)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(13)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(13)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(13)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(13)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(13).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(13).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(13)+"') as periode,\n" +
"			('"+m0.get(13)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"		union\n" +
"		select \n" +
"		*\n" +
"		from \n" +
"		(\n" +
"			select \n" +
"			('"+txt0.get(14)+"') as periode_awal_txt,\n" +
"			('"+txt1.get(14)+"') as periode_akhir_txt,\n" +
"			('"+m0.get(14)+"') as bln_periode_awal_txt,\n" +
"			('"+m1.get(14)+"') as bln_periode_akhir_txt,\n" +
"			(@pAwal:=cast('"+d0.get(14).dateTimeDB()+"' as datetime)) as periode_awal,\n" +
"			(@pAkhir:=cast('"+d1.get(14).dateTimeDB()+"' as datetime)) as periode_akhir,\n" +
"			(@p0:=date_add(@pAwal,interval -1 second)) as periode_0,\n" +
"			('"+tahun+"') as ta,\n" +
"			('"+per.get(14)+"') as periode,\n" +
"			('"+m0.get(14)+"') as id\n" +
"		)q0\n" +
"		join \n" +
"			(\n" +
"				select \n" +
"				if(nm_tu_brg is null or nm_tu_brg='',jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nm_pengguna_brg ,nm_tu_brg) as nm_pengguna_brg,\n" +
"				if(nm_tu_brg is null or nm_tu_brg='',nip_pengguna_brg ,nip_tu_brg) as nip_pengguna_brg,\n" +
"				jab_pengurus_brg,\n" +
"				nm_pengurus_brg,\n" +
"				nip_pengurus_brg\n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"				p_tb_mutasi ptm on ptmdb .id_mutasi =ptm .id_mutasi \n" +
"				where \n" +
"				ptm.tgl_mutasi <=@pAkhir\n" +
"				order by \n" +
"				ptm.tgl_mutasi desc \n" +
"				limit 1\n" +
"			)qTT\n" +
"	)qUnion";
            
            
            
            return ret;
        } catch (ParseException ex) {
            Logger.getLogger(QueryHelperPersediaan.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public static String qRptMutasi(String date0, String date1){
        JMDate d0=null;
        JMDate d1=null;
        String txt0="";
        String txt1="";
        String m0="";
        String m1="";
        try {
            d0=JMDate.create(date0);
            d1=JMDate.create(date1);
            txt0=d0.dateFull();
            txt1=d1.dateFull();
            m0=d0.getMonthString();
            m1=d1.getMonthString();
        } catch (ParseException ex) {
            
        }
        
        
        
        

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
"if(total_akhir is not null,total_akhir,0) as total_akhir,\n" +
"periode_awal,\n" +
"periode_akhir,\n" +
"periode_awal_txt,\n" +
"periode_akhir_txt\n" +
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
"qqAkhir.jab_pengguna_brg,\n" +
"qqAkhir.nm_pengguna_brg,\n" +
"qqAkhir.nip_pengguna_brg,\n" +
"qqAkhir.jab_pengurus_brg,\n" +
"qqAkhir.nm_pengurus_brg,\n" +
"qqAkhir.nip_pengurus_brg,\n" +
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
"	(\n" +
"		select \n" +
"		pti.id_item ,\n" +
"		pti.nm_item ,\n" +
"		pti.satuan ,\n" +
"		pti.id_kat ,\n" +
"		p_q_ref_kat.ket_kat ,\n" +
"		p_q_ref_kat.id_tipe ,\n" +
"		p_q_ref_kat.ket_tipe \n" +
"		from \n" +
"		p_tb_item pti \n" +
"		join\n" +
"			(\n" +
"				select \n" +
"				prk .id_kat ,\n" +
"				prk .ket_kat ,\n" +
"				prk .id_tipe ,\n" +
"				prt .ket_tipe\n" +
"				from \n" +
"				p_ref_kat prk \n" +
"				join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"			)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"	)p_q_item\n" +
"left join\n" +
"	(\n" +
"		select \n" +
"		id_item ,\n" +
"		qty,\n" +
"		sat_qty, \n" +
"		total_qty\n" +
"		from\n" +
"		(select \n" +
"		id_mutasi,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		sum(stok_akhir) as qty,\n" +
"		group_concat(sat_qty order by wkt_masuk_item asc separator ' +\\n') as sat_qty, \n" +
"		sum(total_qty) as total_qty\n" +
"		from\n" +
"		(select \n" +
"		pqmdbb.*,\n" +
"		if(stok_akhir='0',NULL,CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00'))) as sat_qty,\n" +
"		(stok_akhir *harga_satuan) as total_qty\n" +
"		from \n" +
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)pqmdbb \n" +
"		where\n" +
"		tgl_mutasi <=@p0\n" +
"		order by\n" +
"		id_item asc,\n" +
"		tgl_mutasi desc,\n" +
"		id_mutasi desc,\n" +
"		wkt_masuk_item asc)qSubItem\n" +
"		group by\n" +
"		id_mutasi,\n" +
"		id_item \n" +
"		order by\n" +
"		id_item asc,\n" +
"		tgl_mutasi desc,\n" +
"		id_mutasi desc)qItem\n" +
"		group by\n" +
"		id_item\n" +
"	)qqAwal on p_q_item.id_item =qqAwal.id_item\n" +
"left join \n" +
"	(\n" +
"		select \n" +
"		id_item ,\n" +
"		sum(qty) as qty,\n" +
"		group_concat(sat_qty order by wkt_masuk_item asc separator ' +\\n') as sat_qty, \n" +
"		sum(total_qty) as total_qty\n" +
"		from \n" +
"		(\n" +
"		select \n" +
"		pqmdb.id_subitem ,\n" +
"		pqmdb.id_item ,\n" +
"		pqmdb.nm_item ,\n" +
"		pqmdb.harga_satuan ,\n" +
"		pqmdb.wkt_masuk_item,\n" +
"		sum(qty) as qty,\n" +
"		CONCAT(FORMAT(sum(qty) ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"		(sum(qty) *harga_satuan) as total_qty\n" +
"		from \n" +
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)pqmdb \n" +
"		where \n" +
"		debit ='1' and \n" +
"		qty>'0' and \n" +
"		tgl_mutasi >=@pAwal and \n" +
"		tgl_mutasi <=@pAkhir \n" +
"		group by \n" +
"		id_subitem \n" +
"		order by wkt_masuk_item asc\n" +
"		)qDet\n" +
"		group by id_item \n" +
"	)qqMasuk on p_q_item.id_item =qqMasuk.id_item\n" +
"left join \n" +
"	(\n" +
"		select \n" +
"		id_item ,\n" +
"		sum(qty) as qty,\n" +
"		group_concat(sat_qty order by wkt_masuk_item asc separator ' +\\n') as sat_qty, \n" +
"		sum(total_qty) as total_qty\n" +
"		from \n" +
"		(\n" +
"		select \n" +
"		pqmdb.id_subitem ,\n" +
"		pqmdb.id_item ,\n" +
"		pqmdb.nm_item ,\n" +
"		pqmdb.harga_satuan ,\n" +
"		pqmdb.wkt_masuk_item,\n" +
"		sum(qty) as qty,\n" +
"		CONCAT(FORMAT(sum(qty) ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')) as sat_qty,\n" +
"		(sum(qty) *harga_satuan) as total_qty\n" +
"		from \n" +
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)pqmdb \n" +
"		where \n" +
"		debit ='0' and \n" +
"		qty>'0' and \n" +
"		tgl_mutasi >=@pAwal and \n" +
"		tgl_mutasi <=@pAkhir \n" +
"		group by \n" +
"		id_subitem \n" +
"		order by wkt_masuk_item asc\n" +
"		)qDet\n" +
"		group by id_item  \n" +
"	)qqKeluar on p_q_item.id_item =qqKeluar.id_item\n" +
"left join \n" +
"	(\n" +
"		select \n" +
"		id_item ,\n" +
"		qty,\n" +
"		sat_qty, \n" +
"		total_qty,\n" +
"		nm_pengguna_brg,\n" +
"		nip_pengguna_brg,\n" +
"		jab_pengguna_brg,\n" +
"		nm_pengurus_brg,\n" +
"		nip_pengurus_brg,\n" +
"		jab_pengurus_brg\n" +
"		from\n" +
"		(select \n" +
"		id_mutasi,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		sum(stok_akhir) as qty,\n" +
"		group_concat(sat_qty order by wkt_masuk_item asc separator ' +\\n') as sat_qty, \n" +
"		sum(total_qty) as total_qty,\n" +
"		if(nm_tu_brg='' or nm_tu_brg is null,nm_pengguna_brg,nm_tu_brg) as nm_pengguna_brg,\n" +
"		if(nm_tu_brg='' or nm_tu_brg is null,nip_pengguna_brg,nip_tu_brg) as nip_pengguna_brg,\n" +
"		if(nm_tu_brg='' or nm_tu_brg is null,jab_pengguna_brg,jab_tu_brg) as jab_pengguna_brg,\n" +
"		nm_pengurus_brg,\n" +
"		nip_pengurus_brg,\n" +
"		jab_pengurus_brg\n" +
"		from\n" +
"		(select \n" +
"		pqmdbb.*,\n" +
"		if(stok_akhir='0',NULL,CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00'))) as sat_qty,\n" +
"		(stok_akhir *harga_satuan) as total_qty\n" +
"		from \n" +
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)pqmdbb \n" +
"		where\n" +
"		tgl_mutasi <=@pAkhir\n" +
"		order by\n" +
"		id_item asc,\n" +
"		tgl_mutasi desc,\n" +
"		id_mutasi desc,\n" +
"		wkt_masuk_item asc)qSubItem\n" +
"		group by\n" +
"		id_mutasi,\n" +
"		id_item \n" +
"		order by\n" +
"		id_item asc,\n" +
"		tgl_mutasi desc,\n" +
"		id_mutasi desc)qItem\n" +
"		group by\n" +
"		id_item\n" +
"	)qqAkhir on p_q_item.id_item =qqAkhir.id_item\n" +
")qFilter\n" +
"where \n" +
"(qty_awal>0 or qty_masuk>0 or qty_keluar>0) and \n" +
"(qty_awal is not null or qty_masuk is not null or qty_keluar is not null)\n" +
")qFinal\n" +
"order by nm_item asc";




        return ret;
    }
    
    public static String XXXqTmp="select \n" +
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
    
    
    public static String qRptKartu(String date0,String date1, String idItem){
        String ret="select * from\n" +
"	(\n" +
"		select \n" +
"		p_q_item.id_item,\n" +
"		p_q_item.nm_item ,\n" +
"		(NULL)id_mutasi,\n" +
"		(NULL)tgl_mutasi,\n" +
"		('(SALDO AWAL)')uraian,\n" +
"		if(qItemAwal.qty is null,0,qItemAwal.qty) as stok_awal,\n" +
"		if(qItemAwal.sat_qty is null,'-',qItemAwal.sat_qty) as sat_awal,\n" +
"		if(qItemAwal.total_qty is null,0,qItemAwal.total_qty) as total_awal,\n" +
"		CAST('0' as DOUBLE) as qty_masuk,\n" +
"		('-') as sat_masuk,\n" +
"		CAST('0' as DOUBLE) as total_masuk,\n" +
"		CAST('0' as DOUBLE) as qty_keluar,\n" +
"		('-') as sat_keluar,\n" +
"		CAST('0' as DOUBLE) as total_keluar,\n" +
"		if(qItemAwal.qty is null,0,qItemAwal.qty) as stok_akhir,\n" +
"		if(qItemAwal.sat_qty is null,'-',qItemAwal.sat_qty) as sat_akhir,\n" +
"		if(qItemAwal.total_qty is null,0,qItemAwal.total_qty) as total_akhir\n" +
"		from \n" +
"			(\n" +
"				select \n" +
"				pti.id_item ,\n" +
"				pti.nm_item ,\n" +
"				pti.satuan ,\n" +
"				pti.id_kat ,\n" +
"				p_q_ref_kat.ket_kat ,\n" +
"				p_q_ref_kat.id_tipe ,\n" +
"				p_q_ref_kat.ket_tipe \n" +
"				from \n" +
"				p_tb_item pti \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						prk .id_kat ,\n" +
"						prk .ket_kat ,\n" +
"						prk .id_tipe ,\n" +
"						prt .ket_tipe\n" +
"						from \n" +
"						p_ref_kat prk \n" +
"						join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"					)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"			)p_q_item\n" +
"		left join\n" +
"		(select\n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		qty,\n" +
"		sat_qty,\n" +
"		total_qty\n" +
"		from\n" +
"		(select\n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		sum(qty) as qty ,\n" +
"		group_concat(sat_qty separator ' +\\n')as sat_qty ,\n" +
"		sum(total_qty) as total_qty\n" +
"		from\n" +
"		(select \n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		harga_satuan ,\n" +
"		stok_akhir as qty ,\n" +
"		IF(stok_akhir>0,CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')),NULL) as sat_qty ,\n" +
"		(stok_akhir*harga_satuan) as total_qty\n" +
"		from \n" +
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)pqmdb\n" +
"		where tgl_mutasi <'"+date0+"'\n" +
"		order by id_item asc, tgl_mutasi desc, id_mutasi desc)qDet\n" +
"		group by \n" +
"		id_mutasi ,\n" +
"		id_item\n" +
"		order by id_item asc, tgl_mutasi desc, id_mutasi desc)qDetItem\n" +
"		group by id_item)qItemAwal on p_q_item.id_item = qItemAwal.id_item\n" +
"		where p_q_item.id_item ='"+idItem+"'\n" +
"	)qAwal\n" +
"union\n" +
"select * from\n" +
"	(\n" +
"		select\n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		uraian,\n" +
"		sum(stok_awal) as stok_awal ,\n" +
"		if(group_concat(sat_awal separator ' +\\n') is null,'-',group_concat(sat_awal separator ' +\\n')) as sat_awal,\n" +
"		sum(total_awal) as total_awal,\n" +
"		sum(qty_masuk) as qty_masuk,\n" +
"		if(group_concat(sat_masuk separator ' +\\n') is null,'-',group_concat(sat_masuk separator ' +\\n')) as sat_masuk,\n" +
"		sum(total_masuk) as total_masuk,\n" +
"		sum(qty_keluar) as qty_keluar,\n" +
"		if(group_concat(sat_keluar separator ' +\\n') is null,'-',group_concat(sat_keluar separator ' +\\n')) as sat_keluar,\n" +
"		sum(total_keluar) as total_keluar,\n" +
"		sum(stok_akhir) as stok_akhir,\n" +
"		if(group_concat(sat_akhir separator ' +\\n') is null,'-',group_concat(sat_akhir separator ' +\\n')) as sat_akhir,\n" +
"		sum(total_akhir) as total_akhir\n" +
"		from\n" +
"		(select \n" +
"		id_item ,\n" +
"		nm_item ,\n" +
"		id_mutasi ,\n" +
"		tgl_mutasi ,\n" +
"		REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(struktur_ket,'[JAB]',jab_pihak2),'[BIDANG]',nm_bidang),'[BA]',no_ba_mutasi),'[NAMA]',nm_pihak2),'[INSTANSI]',instansi_pihak2) as uraian,\n" +
"		debit ,\n" +
"		harga_satuan ,\n" +
"		stok_awal ,\n" +
"		IF(stok_awal>0,CONCAT(FORMAT(stok_awal ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')),NULL) as sat_awal ,\n" +
"		(stok_awal*harga_satuan)as total_awal,\n" +
"		qty ,\n" +
"		if(debit='1',qty,0)as qty_masuk,\n" +
"		if(debit='1',\n" +
"			IF(qty>0,CONCAT(FORMAT(qty ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')),NULL)\n" +
"		,\n" +
"			NULL\n" +
"		)as sat_masuk,\n" +
"		if(debit='1',qty*harga_satuan,0)as total_masuk,\n" +
"		if(debit='0',qty,0)as qty_keluar,\n" +
"		if(debit='0',\n" +
"			IF(qty>0,CONCAT(FORMAT(qty ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')),NULL)\n" +
"		,\n" +
"			NULL\n" +
"		)as sat_keluar,\n" +
"		if(debit='0',qty*harga_satuan,0)as total_keluar,\n" +
"		stok_akhir,\n" +
"		IF(stok_akhir>0,CONCAT(FORMAT(stok_akhir ,2,'#,##0.00'),' @Rp.',FORMAT(harga_satuan,2,'#,##0.00')),NULL) as sat_akhir ,\n" +
"		(stok_akhir*harga_satuan)as total_akhir\n" +
"		from \n" +
"			(\n" +
"				select \n" +
"				ptmdb.id_det_mutasi_buku ,\n" +
"				ptmdb.id_mutasi ,\n" +
"				p_q_mutasi.tgl_mutasi ,\n" +
"				p_q_mutasi.no_ba_mutasi ,\n" +
"				p_q_mutasi.id_jenis_mutasi ,\n" +
"				p_q_mutasi.ket_jenis_mutasi ,\n" +
"				p_q_mutasi.debit ,\n" +
"				p_q_mutasi.struktur_ket ,\n" +
"				p_q_mutasi.nm_pihak2 ,\n" +
"				p_q_mutasi.nip_pihak2 ,\n" +
"				p_q_mutasi.almt_pihak2 ,\n" +
"				p_q_mutasi.jab_pihak2 ,\n" +
"				p_q_mutasi.instansi_pihak2 ,\n" +
"				p_q_mutasi.nm_pengguna_brg ,\n" +
"				p_q_mutasi.nip_pengguna_brg ,\n" +
"				p_q_mutasi.jab_pengguna_brg ,\n" +
"				p_q_mutasi.nm_tu_brg ,\n" +
"				p_q_mutasi.nip_tu_brg ,\n" +
"				p_q_mutasi.jab_tu_brg ,\n" +
"				p_q_mutasi.nm_pengurus_brg ,\n" +
"				p_q_mutasi.nip_pengurus_brg ,\n" +
"				p_q_mutasi.jab_pengurus_brg ,\n" +
"				p_q_mutasi.no_dok_dasar ,\n" +
"				p_q_mutasi.tgl_dok_dasar ,\n" +
"				p_q_mutasi.no_dok_dasar2 ,\n" +
"				p_q_mutasi.tgl_dok_dasar2 ,\n" +
"				p_q_mutasi.id_bidang ,\n" +
"				p_q_mutasi.nm_bidang ,\n" +
"				p_q_mutasi.approved ,\n" +
"				p_q_mutasi.printed ,\n" +
"				p_q_subitem.id_subitem ,\n" +
"				p_q_subitem.wkt_masuk_item ,\n" +
"				p_q_subitem.id_item ,\n" +
"				p_q_subitem.nm_item ,\n" +
"				p_q_subitem.satuan ,\n" +
"				p_q_subitem.id_kat ,\n" +
"				p_q_subitem.ket_kat ,\n" +
"				p_q_subitem.id_tipe ,\n" +
"				p_q_subitem.ket_tipe ,\n" +
"				p_q_subitem.harga_satuan ,\n" +
"				ptmdb.qty ,\n" +
"				ptmdb.stok_awal ,\n" +
"				ptmdb.stok_akhir \n" +
"				from \n" +
"				p_tb_mutasi_det_buku ptmdb \n" +
"				join\n" +
"					(\n" +
"						select \n" +
"						ptm.id_mutasi ,\n" +
"						ptm.tgl_mutasi ,\n" +
"						ptm.no_ba_mutasi ,\n" +
"						ptm.id_jenis_mutasi ,\n" +
"						prjm.ket_jenis_mutasi ,\n" +
"						prjm.debit ,\n" +
"						prjm.struktur_ket ,\n" +
"						ptm.nm_pihak2 ,\n" +
"						ptm.nip_pihak2 ,\n" +
"						ptm.almt_pihak2 ,\n" +
"						ptm.jab_pihak2 ,\n" +
"						ptm.instansi_pihak2 ,\n" +
"						ptm.nm_pengguna_brg ,\n" +
"						ptm.nip_pengguna_brg ,\n" +
"						ptm.jab_pengguna_brg ,\n" +
"						ptm.nm_tu_brg ,\n" +
"						ptm.nip_tu_brg ,\n" +
"						ptm.jab_tu_brg ,\n" +
"						ptm.nm_pengurus_brg ,\n" +
"						ptm.nip_pengurus_brg ,\n" +
"						ptm.jab_pengurus_brg ,\n" +
"						ptm.no_dok_dasar ,\n" +
"						ptm.tgl_dok_dasar ,\n" +
"						ptm.no_dok_dasar2 ,\n" +
"						ptm.tgl_dok_dasar2 ,\n" +
"						ptm.id_bidang ,\n" +
"						ptb.nm_bidang ,\n" +
"						ptm.approved ,\n" +
"						ptm.printed \n" +
"						from \n" +
"						p_tb_mutasi ptm \n" +
"						join p_ref_jenis_mutasi prjm on ptm.id_jenis_mutasi =prjm .id_jenis_mutasi \n" +
"						join p_tb_bidang ptb on ptm.id_bidang =ptb .id_bidang \n" +
"					)p_q_mutasi on ptmdb.id_mutasi =p_q_mutasi.id_mutasi \n" +
"				JOIN \n" +
"					(\n" +
"						select \n" +
"						pts.id_subitem ,\n" +
"						pts.wkt_masuk_item ,\n" +
"						pts.id_item ,\n" +
"						p_q_item.nm_item ,\n" +
"						p_q_item.satuan ,\n" +
"						p_q_item.id_kat ,\n" +
"						p_q_item.ket_kat ,\n" +
"						p_q_item.id_tipe ,\n" +
"						p_q_item.ket_tipe ,\n" +
"						pts.harga_satuan \n" +
"						from \n" +
"						p_tb_subitem pts \n" +
"						join\n" +
"							(\n" +
"								select \n" +
"								pti.id_item ,\n" +
"								pti.nm_item ,\n" +
"								pti.satuan ,\n" +
"								pti.id_kat ,\n" +
"								p_q_ref_kat.ket_kat ,\n" +
"								p_q_ref_kat.id_tipe ,\n" +
"								p_q_ref_kat.ket_tipe \n" +
"								from \n" +
"								p_tb_item pti \n" +
"								join\n" +
"									(\n" +
"										select \n" +
"										prk .id_kat ,\n" +
"										prk .ket_kat ,\n" +
"										prk .id_tipe ,\n" +
"										prt .ket_tipe\n" +
"										from \n" +
"										p_ref_kat prk \n" +
"										join p_ref_tipe prt on prk.id_tipe =prt.id_tipe \n" +
"									)p_q_ref_kat on pti.id_kat =p_q_ref_kat.id_kat \n" +
"							)p_q_item on pts.id_item =p_q_item.id_item \n" +
"					)p_q_subitem on ptmdb.id_subitem =p_q_subitem.id_subitem  \n" +
"			)pqmdb\n" +
"		where\n" +
"		(stok_awal>'0' or qty>'0') and\n" +
"		tgl_mutasi>='"+date0+"' and\n" +
"		tgl_mutasi<='"+date1+"'\n" +
"		order by\n" +
"		id_item asc,\n" +
"		tgl_mutasi asc,\n" +
"		id_mutasi asc,\n" +
"		wkt_masuk_item asc)qDet\n" +
"		where id_item ='"+idItem+"'\n" +
"		group by \n" +
"		id_item ,\n" +
"		id_mutasi \n" +
"		order by\n" +
"		id_item asc,\n" +
"		tgl_mutasi asc,\n" +
"		id_mutasi asc\n" +
"	)qIsi";
        
        
        
        return ret;
    }
    
}

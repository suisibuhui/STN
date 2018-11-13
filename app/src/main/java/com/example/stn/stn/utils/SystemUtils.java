package com.example.stn.stn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Xml;
import android.view.Gravity;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


import com.example.stn.stn.cache.LocationCache;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Name: SystemUtils
 * Author: xulong
 * Comment: //系统工具类
 * Date: 2016-07-14 16:59.
 */
public class SystemUtils {
    private static Toast toast;

    /**
     * 显示Toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        SystemUtils.showToast(context, message, Gravity.BOTTOM);
    }

    /**
     * 显示Toast，并调整位置
     *
     * @param context
     * @param message
     * @param gravity
     */
    public static void showToast(Context context, String message, int gravity) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }

        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }

        toast.setGravity(gravity, 0, 0);
        toast.show();
    }


    /**
     * 获取当前时间的毫秒数
     */


    /**
     * 检查是否输入框为空.
     *
     * false :为空;
     *
     * true：不为空
     */
    public static boolean checkNull(String edit) {
        if (edit == null || edit.length() == 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 检查是否输入框为空
     * false : 不为空
     * true：为空
     */
    public static boolean isNull(String edit) {
        if (edit == null || edit.length() == 0) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 防止按钮点击多次
     * ：若果间隔小于0.5S返回true
     */

    private static long lastClickTime;    //最后点击时间
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 获取业务类型Id
     */
    public static String getBusinessId (String businessName) {
        switch (businessName) {
            case "newAppointment":  //预约新装
                return "800001060015";
            case "newBusiness":     //业务新装
                return "800001000001";
            case "bookingChange":  //预约套餐变更
                return "800001060028";
            case "packageChange":     //套餐变更
                return "800001000049";
            case "renewPackages":  //套餐续订
                return "800001060023";
            case "pauseRecovery":  //暂停恢复
                return "800001000033";
            case "maintenance":  //修改账单计划
                return "800001060017";
            case "customerDataMaintenance":  //客户资料维护
                return "800001000043";

            default:
                return "未找到相应业务";
        }
    }
    /**
     * 获取业务类型Id
     */
    public static String getBusinessName (String businessName) {
        switch (businessName) {
            case "newAppointment":  //预约新装
                return "预约新装";
            case "newBusiness":     //业务新装
                return "业务新装";
            case "bookingChange":  //预约套餐变更
                return "预约套餐变更";
            case "packageChange":     //套餐变更
                return "套餐变更";
            case "renewPackages":  //套餐续订
                return "套餐续订";
            case "pauseRecovery":  //暂停恢复
                return "暂停恢复";
            case "maintenance":  //修改账单计划
                return "修改账单计划";
            case "customerDataMaintenance":  //客户资料维护
                return "客户资料维护";
            case "appointmentTransfer":  //客户资料维护
                return "工单处理";
            default:
                return "未找到相应业务";
        }
    }
    public static String getState (String businessName) {
        switch (businessName) {
            case "11":  //预约新装
                return "移机暂停";
            case "1":     //业务新装
                return "预开通";
            case "2":  //预约套餐变更
                return "开通";
            case "3":     //套餐变更
                return "暂停";
            case "renewPackages":  //套餐续订
                return "套餐续订";
            case "pauseRecovery":  //暂停恢复
                return "暂停恢复";
            case "4":  //修改账单计划
                return "管理停机";
            case "5":  //客户资料维护
                return "缓装";
            case "12":  //客户资料维护
                return "撤销";
            case "7":  //客户资料维护
                return "预开户暂停";
            case "8":  //客户资料维护
                return "预销户";
            case "9":  //客户资料维护
                return "待销户";
            case "10":  //客户资料维护
                return "销户";
            case "13":  //客户资料维护
                return "空关";
            case "6":  //客户资料维护
                return "移机";
            default:
                return "未找到相应业务";
        }
    }
    public static String getOpenCloseState(String businessName){

        if(businessName.equals("000000000000000000000000000000")){
            return "正常";
        }else{
            String string1 = "1_"+businessName.substring(0, 5);
            String string2 = "2_"+businessName.substring(5, 10);
            String string3 = "3_"+businessName.substring(10, 15);
            String string4 = "4_"+businessName.substring(15, 20);
            StringBuffer result  = new StringBuffer();
            String[] a = {string1,string2,string3,string4};
            for (int i = 0;i< 4;i++){
                switch(a[i]){
                    case "1_10000":
                        result.append("暂停停机  ");
                    break;
                    case "2_10000":
                        result.append("账务欠费停机  ");
                        break;
                    case "2_01000":
                        result.append("使用费不足停机  ");
                        break;
                    case "3_10000":
                        result.append("管理停机  ");
                        break;
                    case "3_01000":
                        result.append("停模停机  ");
                        break;
                    case "3_00100":
                        result.append("已搬停机  ");
                        break;
                    case "3_00010":
                        result.append("拆房停机  ");
                        break;
                    case "4_10000":
                        result.append("点播限制  ");
                        break;

                }


            }
            return result.toString();
        }



    }
    public static String getPackageState (String businessName) {
        switch (businessName) {
            case "11":  //预约新装
                return "移机暂停";
            case "1":     //业务新装
                return "预开通";
            case "2":  //预约套餐变更
                return "开通";
            case "3":     //套餐变更
                return "暂停";
            case "renewPackages":  //套餐续订
                return "套餐续订";
            case "pauseRecovery":  //暂停恢复
                return "暂停恢复";
            case "4":  //修改账单计划
                return "管理停机";
            case "5":  //客户资料维护
                return "缓装";
            case "12":  //客户资料维护
                return "撤销";
            case "7":  //客户资料维护
                return "预开户暂停";
            case "8":  //客户资料维护
                return "预销户";
            case "9":  //客户资料维护
                return "待销户";
            case "10":  //客户资料维护
                return "销户";
            case "13":  //客户资料维护
                return "空关";
            case "6":  //客户资料维护
                return "移机";
            case "14":  //客户资料维护
                return "自动销户";
            default:
                return "未找到相应业务";
        }
    }

    public static String getIdType(String type){

        switch(type){
            case "0":
                return "其他";
            case "11":
                return "身份证";
            case "12":
                return "护照";
            case "13":
                return "户口本";
            case "14":
                return "暂住证";
            case "15":
                return "军官证";
            case "16":
                return "学生证";
            case "17":
                return "教师证";
            case "19":
                return "用户证";
            case "20":
                return "房产证";
            case "21":
                return "购房合同";
            case "22":
                return "工作证";
            case "31":
                return "营业执照";
            case "32":
                return "驾驶证";
            case "33":
                return "文职证";

            default:
                return "未找到";
        }


    }
    public static String getPayType (String businessName) {
        switch (businessName) {
            case "1":  //营业厅现金
                return "营业厅现金";
            case "10":     //卡充值
                return "卡充值";
            case "11":  //市民卡POS
                return "市民卡POS";
            case "12":     //转帐
                return "转帐";
            case "13":  //UPG家银通支付
                return "UPG家银通支付";
            case "14":  //商盟卡POS
                return "商盟卡POS";
            case "15":  //工行积分兑换
                return "工行积分兑换";
            case "16":  //工行分期付款
                return "工行分期付款";
            case "17":  //代收费
                return "代收费";
            case "19":  //零配件缴费
                return "零配件缴费";
            case "2":  //营业厅POS
                return "营业厅POS";
            case "20":  //续费返充退款缴费
                return "续费返充退款缴费";
            case "21":  //续费返充
                return "续费返充";
            case "22":  //集中缴费
                return "集中缴费";
            case "23":  //账户资金转移
                return "账户资金转移";
            case "24":  //赔款-存入账户
                return "赔款-存入账户";
            case "3":  //支票
                return "支票";
            case "33":  //混合缴费
                return "混合缴费";
            case "34":  //天猫订购充值
                return "天猫订购充值";
            case "35":  //工行MISPOS
                return "工行MISPOS";
            case "36":  //支付宝扫码支付
                return "支付宝扫码支付";
            case "39":  //集团托收续费
                return "集团托收续费";
            case "4":  //银行现金
                return "银行现金";
            case "40":  //集团现金托收
                return "集团现金托收";
            case "5":  //现金托收
                return "现金托收";
            case "6":  //现金冲值卡
                return "现金冲值卡";
            case "7":  //UPG现金充值
                return "UPG现金充值";
            case "8":  //预存费存帐本
                return "预存费存帐本";
            case "9":  //营业厅点卡
                return "营业厅点卡";
            default:
                return "未找到相应业务";
        }
    }

    /**
     * 根据值, 设置spinner默认选中值:
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value){
        SpinnerAdapter spinnerAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
        int k= spinnerAdapter.getCount();
        for( int i = 0; i < k; i ++ ) {
            if(value.equals(spinnerAdapter.getItem(i).toString())){
                spinner.setSelection(i,true);// 默认选中项
                break;
            }
        }
    }


    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDouble(String str) {
        double d = 0;
        if(SystemUtils.checkNull(str)) {
            d = Double.valueOf(str);
        }
        DecimalFormat df  = new DecimalFormat("0.00");
        return df.format(d);
    }




    public static String getNewData(){
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH)+1;
        int day = instance.get(Calendar.DAY_OF_MONTH);
        //获取当前时间
        String str = "";
        str = year+"-"+month+"-"+day;
        return str;
    }



}

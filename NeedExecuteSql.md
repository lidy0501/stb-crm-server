
(已执行)
use crm;
alter table CRM_ORDER drop column PRODUCT_NAME;
alter table CRM_ORDER drop column PRODUCT_SPE;
alter table CRM_ORDER drop column PRODUCT_NUM;
alter table CRM_ORDER alter column ORDER_STATE set default 0;
alter table CRM_ORDER modify TOTAL_FEE int default 0 null comment '订单应付总金额，单位：分';
alter table CRM_ORDER modify DOWN_PAY_FEE int default 0 null comment '已付金额，单位：分';
alter table CRM_ORDER modify FINAL_PAY_FEE int null comment '待付金额，单位：分';
alter table CRM_ORDER drop column PAY_PROGRESS;

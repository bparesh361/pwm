select * from mst_promo mp where mp.promo_id=14756

select * from trans_promo tp where tp.promo_id=14756

select tp.trans_promo_id as ticket_no, ms.status_desc as previous_status, ms1.status_desc as new_status, emp.employee_name, tps.updated_date, tps.remarks
from trans_promo_status tps inner join trans_promo tp on tp.trans_promo_id=tps.trans_promo_id
inner join mst_status ms on tps.previous_status_id = ms.status_id 
inner join mst_status ms1 on tps.new_status_id = ms1.status_id 
inner join mst_employee emp on emp.emp_id = tps.updated_by
where tps.updated_date between '2014-08-01' and '2014-08-27';

select * from mst_proposal order by proposal_id desc

desc mst_report_email
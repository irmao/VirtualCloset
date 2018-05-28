alter table vc_closet add column category varchar(150);
update vc_closet set category = 'LOOK';
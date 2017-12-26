insert into vc_sector values(1, 'Chapéu', true, true);
insert into vc_sector values(2, 'Camisa', true, false);
insert into vc_sector values(3, 'Calça', true, true);
insert into vc_sector values(4, 'Short', true, true);
insert into vc_sector values(5, 'Vestido', true, true);
insert into vc_sector values(6, 'Saia', true, true);
insert into vc_sector values(7, 'Sapato', true, true);
insert into vc_sector values(8, 'Meia-calça', true, false);
insert into vc_sector values(9, 'Blusa', true, true);

insert into vc_sector_body_position values(1, 'HEAD');
insert into vc_sector_body_position values(2, 'TOP');
insert into vc_sector_body_position values(3, 'BOTTOM');
insert into vc_sector_body_position values(4, 'BOTTOM');
insert into vc_sector_body_position values(5, 'TOP');
insert into vc_sector_body_position values(5, 'BOTTOM');
insert into vc_sector_body_position values(6, 'BOTTOM');
insert into vc_sector_body_position values(7, 'FOOT');
insert into vc_sector_body_position values(8, 'BOTTOM');
insert into vc_sector_body_position values(9, 'TOP');

insert into vc_user values(1,'system','donotreply@virtualcloset.com','123');
alter sequence vc_user_seq restart with 2;


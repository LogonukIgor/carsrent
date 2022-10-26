alter table cars_rent.driving_licence
alter column date_of_issue type timestamp(6) using date_of_issue::timestamp(6);

alter table cars_rent.driving_licence
alter column valid_until type timestamp(6) using valid_until::timestamp(6);
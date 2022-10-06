drop function if exists get_deal_stats_number_of_open_deal();

create or replace function get_deal_stats_number_of_open_deal() returns integer
    language sql
as
$$
select count(id)
from cars_rent.deal
where is_deleted = false;
$$;

alter function get_deal_stats_number_of_open_deal() owner to postgres;
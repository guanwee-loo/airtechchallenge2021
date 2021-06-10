import json
from jinja2 import Template

with open('sid.json','r') as handle:
	recs=json.load(handle)

total=len(recs)
cypher_template="""
{% set i=namespace(value=0) %}
{% set le= recs | length %}
{% for rec in recs %}
merge (a{{i.value}}:Airport { name : "{{ rec["airport"]["name"] }}",  uid : "{{ rec["airport"]["uid"] }}",  lat : "{{ rec["airport"]["lat"] }}" })
{% set wp_ctr = rec["waypoints"] | length %}
merge (wp{{0}}{{i.value}}: Waypoint { name : "{{ rec["waypoints"][0].name }}", uid : "{{ rec["waypoints"][0].name }}" } ) 
merge (a{{i.value}}) - [:HAS_ROUTE_TO {SID : "{{ rec["name"] }}"} ] -> (wp{{0}}{{i.value}})
{% for wp_i in range(1,wp_ctr) %} 
merge (wp{{wp_i}}{{i.value}}: Waypoint { name : "{{ rec["waypoints"][wp_i].name }}", uid : "{{ rec["waypoints"][wp_i].name }}" })
merge (wp{{wp_i - 1}}{{i.value}}) - [:HAS_ROUTE_TO {SID : "{{ rec["name"] }}"} ] -> (wp{{wp_i}}{{i.value}})

{% endfor %}
{% set i.value = i.value +1 %}
{% endfor %}
"""
c = Template(cypher_template)
o=c.render(recs=recs)
print(o)

async function(args) {
/*
created_timestamp(-1)
base_component_id("homepage_3")
is_app(true)
display_name("Homepage 3")
uses_javascript_librararies(["aframe"])
description('Homepage 3')
load_once_from_file(true)
logo_url("https://moe.it.slotshaven.dk/wp/wp-content/uploads/2017/11/homepage.png")
*/

    await load("form_subscribe_to_appshare")
    Vue.component('homepage_3', {

      template:
`<div  class="container" style=''>
Contact@AppShare.co   +45 2859 5405
</div>`
    })
}
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2018_07_17_082247) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "addresses", force: :cascade do |t|
    t.string "city"
    t.string "district"
    t.integer "house_no"
    t.integer "advertisments_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.bigint "advertisment_id"
    t.index ["advertisment_id"], name: "index_addresses_on_advertisment_id"
    t.index ["advertisments_id"], name: "index_addresses_on_advertisments_id"
  end

  create_table "advertisments", force: :cascade do |t|
    t.string "title_post"
    t.float "price"
    t.boolean "status"
    t.integer "users_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.bigint "user_id"
    t.index ["user_id"], name: "index_advertisments_on_user_id"
    t.index ["users_id"], name: "index_advertisments_on_users_id"
  end

  create_table "images", force: :cascade do |t|
    t.string "extra_image_file_name"
    t.string "extra_image_content_type"
    t.integer "extra_image_file_size"
    t.datetime "extra_image_updated_at"
    t.string "bathroom_image_file_name"
    t.string "bathroom_image_content_type"
    t.integer "bathroom_image_file_size"
    t.datetime "bathroom_image_updated_at"
    t.string "kitchen_image_file_name"
    t.string "kitchen_image_content_type"
    t.integer "kitchen_image_file_size"
    t.datetime "kitchen_image_updated_at"
    t.integer "advertisments_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.bigint "advertisment_id"
    t.index ["advertisment_id"], name: "index_images_on_advertisment_id"
    t.index ["advertisments_id"], name: "index_images_on_advertisments_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "full_name"
    t.string "address"
    t.string "email"
    t.string "password"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_foreign_key "addresses", "advertisments"
  add_foreign_key "advertisments", "users"
  add_foreign_key "images", "advertisments"
end
